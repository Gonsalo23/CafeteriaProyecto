package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.dtos.request.PedidoCreateDTO;
import com.safareyes.cafeteria.dtos.response.PedidoResponseDTO;
import com.safareyes.cafeteria.dtos.response.LineaPedidoResponseDTO;
import com.safareyes.cafeteria.dtos.response.ProductoRankingDTO;
import com.safareyes.cafeteria.dtos.response.VentasDiaDTO;
import com.safareyes.cafeteria.excepciones.RecursoNoEncontradoException;
import com.safareyes.cafeteria.excepciones.ReglaNegocioException;
import com.safareyes.cafeteria.mappers.IPedidoMapper;
import com.safareyes.cafeteria.modelos.Cliente;
import com.safareyes.cafeteria.modelos.Cupon;
import com.safareyes.cafeteria.modelos.Descuento;
import com.safareyes.cafeteria.modelos.DetallePedido;
import com.safareyes.cafeteria.modelos.Pedido;
import com.safareyes.cafeteria.modelos.Producto;
import com.safareyes.cafeteria.repositorios.IClienteRepositorio;
import com.safareyes.cafeteria.repositorios.ICuponRepositorio;
import com.safareyes.cafeteria.repositorios.IDescuentoRepositorio;
import com.safareyes.cafeteria.repositorios.IPedidoRepositorio;
import com.safareyes.cafeteria.repositorios.IProductoRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
@Transactional(readOnly = true)
public class PedidoServicioImpl implements IPedidoServicio {

    private static final ZoneId ZONA_CAFETERIA = ZoneId.of("Europe/Madrid");
    private final IPedidoRepositorio pedidoRepo;
    private final IProductoRepositorio productoRepo;
    private final IClienteRepositorio clienteRepo;
    private final ICuponRepositorio cuponRepo;
    private final IDescuentoRepositorio descuentoRepo;
    private final IPedidoMapper pedidoMapper;

    public PedidoServicioImpl(
            IPedidoRepositorio pedidoRepo,
            IProductoRepositorio productoRepo,
            IClienteRepositorio clienteRepo,
            ICuponRepositorio cuponRepo,
            IDescuentoRepositorio descuentoRepo,
            IPedidoMapper pedidoMapper
    ) {
        this.pedidoRepo = pedidoRepo;
        this.productoRepo = productoRepo;
        this.clienteRepo = clienteRepo;
        this.cuponRepo = cuponRepo;
        this.descuentoRepo = descuentoRepo;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PedidoResponseDTO> listarPedidos(
            String estado,
            LocalDate desde,
            LocalDate hasta,
            Pageable pageable
    ) {
        return pedidoRepo.findAll(
                IPedidoRepositorio.filtros(
                        estado,
                        desde == null ? null : desde.atStartOfDay(),
                        hasta == null ? null : hasta.plusDays(1).atStartOfDay()
                ),
                pageable
        ).map(pedidoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPedidoPorId(Integer id) {
        var pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id));
        var lineas = pedidoMapper.toLineaResponseDTO(pedidoRepo.buscarLineasPorPedidoId(id));
        return pedidoMapper.toResponseDTO(pedido, lineas);
    }

    @Override
    @Transactional
    public PedidoResponseDTO crearPedido(PedidoCreateDTO peticion) {
        if (peticion.dniSocio() != null && !peticion.dniSocio().isBlank()) {
            throw new ReglaNegocioException(
                    "El descuento FitZone no está disponible hasta integrar su servicio de validación."
            );
        }

        Map<Integer, Integer> cantidadesPorProducto = new LinkedHashMap<>();
        peticion.lineas().forEach(linea ->
                cantidadesPorProducto.merge(linea.productoId(), linea.cantidad(), Integer::sum));
        if (cantidadesPorProducto.values().stream().anyMatch(cantidad -> cantidad > 20)) {
            throw new ReglaNegocioException(
                    "La cantidad total de un producto no puede superar 20 aunque se repita en varias líneas."
            );
        }

        Map<Integer, Producto> productosPorId = new LinkedHashMap<>();
        productoRepo.findAllById(cantidadesPorProducto.keySet())
                .forEach(producto -> productosPorId.put(producto.getId(), producto));
        if (productosPorId.size() != cantidadesPorProducto.size()) {
            throw new RecursoNoEncontradoException("Uno o varios productos del pedido no existen.");
        }

        LocalDateTime ahora = LocalDateTime.now(ZONA_CAFETERIA);
        Cliente cliente = peticion.clienteId() == null
                ? null
                : clienteRepo.findById(peticion.clienteId())
                        .orElseThrow(() -> new RecursoNoEncontradoException(
                                "Cliente no encontrado con ID: " + peticion.clienteId()));

        BigDecimal subtotal = BigDecimal.ZERO;
        Map<Integer, BigDecimal> preciosPorProducto = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> linea : cantidadesPorProducto.entrySet()) {
            Producto producto = productosPorId.get(linea.getKey());
            if (!Boolean.TRUE.equals(producto.getActivo())) {
                throw new ReglaNegocioException("El producto " + producto.getNombre() + " está inactivo.");
            }
            if (!Boolean.TRUE.equals(producto.getDisponible())) {
                throw new ReglaNegocioException("El producto " + producto.getNombre() + " no está disponible.");
            }

            BigDecimal precio = producto.getPrecio_iva() != null
                    ? producto.getPrecio_iva()
                    : producto.getPrecio();
            if (precio == null || precio.signum() < 0) {
                throw new ReglaNegocioException(
                        "El producto " + producto.getNombre() + " no tiene un precio válido.");
            }
            preciosPorProducto.put(producto.getId(), precio);
            subtotal = subtotal.add(precio.multiply(BigDecimal.valueOf(linea.getValue())));
        }

        Cupon cupon = buscarYValidarCupon(peticion.codigoCupon(), cliente, subtotal, ahora);
        BigDecimal importeDescuento = cupon == null
                ? BigDecimal.ZERO
                : calcularDescuento(cupon, subtotal);
        Descuento descuento = importeDescuento.signum() == 0
                ? null
                : descuentoRepo.save(Descuento.builder()
                        .importe(importeDescuento)
                        .origen("CUPON")
                        .build());

        LocalDateTime inicioDia = ahora.toLocalDate().atStartOfDay();
        Integer turno = pedidoRepo.siguienteNumeroTurno(inicioDia, inicioDia.plusDays(1));
        Pedido pedido = Pedido.builder()
                .numeroTurno(turno)
                .fecha(ahora)
                .estado("pendiente")
                .cliente(cliente)
                .cupon(cupon)
                .descuento(descuento)
                .build();

        cantidadesPorProducto.forEach((productoId, cantidad) -> pedido.agregarDetalle(
                DetallePedido.builder()
                        .producto(productosPorId.get(productoId))
                        .cantidad(cantidad)
                        .precio(preciosPorProducto.get(productoId))
                        .build()
        ));

        Pedido guardado = pedidoRepo.save(pedido);
        log.info("Pedido creado: id={} turno={}", guardado.getId(), guardado.getNumeroTurno());
        var lineas = pedidoMapper.toLineaResponseDTO(pedidoRepo.buscarLineasPorPedidoId(guardado.getId()));
        return pedidoMapper.toResponseDTO(guardado, lineas);
    }

    @Override
    @Transactional(readOnly = true)
    public VentasDiaDTO consultarVentasDia(LocalDate fecha) {
        return pedidoRepo.ventasDia(fecha.atStartOfDay(), fecha.plusDays(1).atStartOfDay());
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<ProductoRankingDTO> consultarProductosMasVendidos(
            LocalDate desde,
            LocalDate hasta,
            int limite
    ) {
        return productoRepo.rankingProductosMasVendidosEntreFechas(
                desde.atStartOfDay(),
                hasta.plusDays(1).atStartOfDay(),
                limite
        );
    }

    private Cupon buscarYValidarCupon(
            String codigo,
            Cliente cliente,
            BigDecimal subtotal,
            LocalDateTime ahora
    ) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        Cupon cupon = cuponRepo.findByCodigoIgnoreCase(codigo.trim())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cupón no encontrado: " + codigo));
        if (cupon.getFechaInicio() != null && ahora.isBefore(cupon.getFechaInicio())
                || cupon.getFechaFin() != null && ahora.isAfter(cupon.getFechaFin())) {
            throw new ReglaNegocioException("El cupón no está vigente.");
        }
        if (cupon.getMaxUsos() != null
                && pedidoRepo.countByCupon_Id(cupon.getId()) >= cupon.getMaxUsos()) {
            throw new ReglaNegocioException("El cupón ha alcanzado su máximo de usos.");
        }
        if (cupon.getImporteMinimo() != null && subtotal.compareTo(cupon.getImporteMinimo()) < 0) {
            throw new ReglaNegocioException(
                    "El pedido no alcanza el importe mínimo para utilizar este cupón."
            );
        }
        if (cupon.getEmailCliente() != null && !cupon.getEmailCliente().isBlank()
                && (cliente == null || cliente.getEmail() == null
                || !cupon.getEmailCliente().trim().equalsIgnoreCase(cliente.getEmail().trim()))) {
            throw new ReglaNegocioException("Este cupón está asociado a otro cliente.");
        }
        return cupon;
    }

    private BigDecimal calcularDescuento(Cupon cupon, BigDecimal subtotal) {
        if (cupon.getValor() == null || cupon.getValor().signum() < 0) {
            throw new ReglaNegocioException("El cupón no tiene un valor de descuento válido.");
        }
        BigDecimal importe = switch (cupon.getTipo() == null ? "" : cupon.getTipo().trim().toLowerCase()) {
            case "porcentaje", "porcentual", "percentage" ->
                    subtotal.multiply(cupon.getValor()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            case "fijo", "importe", "fixed" -> cupon.getValor();
            default -> throw new ReglaNegocioException("El tipo de descuento del cupón no es válido.");
        };
        return importe.min(subtotal).setScale(2, RoundingMode.HALF_UP);
    }
}
