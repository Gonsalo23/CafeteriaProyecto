package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.dtos.request.CuponCreateDTO;
import com.safareyes.cafeteria.dtos.request.CuponValidarDTO;
import com.safareyes.cafeteria.dtos.response.CuponResponseDTO;
import com.safareyes.cafeteria.dtos.response.CuponValidacionResponseDTO;
import com.safareyes.cafeteria.excepciones.ConflictoException;
import com.safareyes.cafeteria.excepciones.RecursoNoEncontradoException;
import com.safareyes.cafeteria.excepciones.ReglaNegocioException;
import com.safareyes.cafeteria.mappers.ICuponMapper;
import com.safareyes.cafeteria.modelos.Cupon;
import com.safareyes.cafeteria.repositorios.ICuponRepositorio;
import com.safareyes.cafeteria.repositorios.IPedidoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CuponServicioImpl implements ICuponServicio {

    private static final ZoneId ZONA_CAFETERIA = ZoneId.of("Europe/Madrid");
    private static final Logger LOGGER = Logger.getLogger(CuponServicioImpl.class.getName());

    private final ICuponRepositorio cuponRepositorio;
    private final IPedidoRepositorio pedidoRepositorio;
    private final ICuponMapper cuponMapper;

    @Override
    @Transactional
    public CuponResponseDTO crearCupon(CuponCreateDTO peticion) {
        String codigo = peticion.codigo().trim().toUpperCase(Locale.ROOT);
        if (cuponRepositorio.existsByCodigoIgnoreCase(codigo)) {
            LOGGER.warning("No se pudo crear el cupón: el código ya existe. codigo=" + codigo);
            throw new ConflictoException("Ya existe un cupón con el código " + codigo + ".");
        }
        if (peticion.fechaFin().isBefore(peticion.fechaInicio())) {
            LOGGER.warning("No se pudo crear el cupón: rango de fechas inválido. codigo=" + codigo);
            throw new ReglaNegocioException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        if ("porcentaje".equalsIgnoreCase(peticion.tipo())
                && peticion.valor().compareTo(new BigDecimal("100")) > 0) {
            LOGGER.warning("No se pudo crear el cupón: porcentaje superior al 100 %. codigo=" + codigo);
            throw new ReglaNegocioException("El descuento porcentual no puede superar el 100 %.");
        }

        Cupon cupon = cuponMapper.toEntity(peticion);
        cupon.setCodigo(codigo);
        cupon.setTipo(peticion.tipo().trim().toLowerCase(Locale.ROOT));
        cupon.setActivo(true);
        Cupon guardado = cuponRepositorio.save(cupon);
        LOGGER.info("Cupón creado: id=" + guardado.getId() + " codigo=" + guardado.getCodigo());
        return cuponMapper.toResponse(guardado);
    }

    @Override
    public CuponValidacionResponseDTO validarCupon(CuponValidarDTO peticion) {
        Cupon cupon = cuponRepositorio.findByCodigoIgnoreCase(peticion.codigo().trim())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el cupón con código " + peticion.codigo()));
        BigDecimal subtotal = peticion.subtotal();
        String motivoInvalidez = obtenerMotivoInvalidez(cupon, peticion, subtotal);

        if (motivoInvalidez != null) {
            LOGGER.info("Validación de cupón rechazada: codigo=" + cupon.getCodigo() + " motivo=" + motivoInvalidez);
            return cuponMapper.toValidacionResponse(
                    cupon, false, BigDecimal.ZERO.setScale(2), subtotal, motivoInvalidez);
        }

        BigDecimal descuento = calcularDescuento(cupon, subtotal);
        BigDecimal total = subtotal.subtract(descuento).setScale(2, RoundingMode.HALF_UP);
        LOGGER.info("Cupón validado: codigo=" + cupon.getCodigo() + " descuento=" + descuento);
        return cuponMapper.toValidacionResponse(
                cupon, true, descuento, total, "Cupón válido.");
    }

    private String obtenerMotivoInvalidez(Cupon cupon, CuponValidarDTO peticion, BigDecimal subtotal) {
        LocalDateTime ahora = LocalDateTime.now(ZONA_CAFETERIA);
        if (!Boolean.TRUE.equals(cupon.getActivo())) {
            return "El cupón está inactivo.";
        }
        if (cupon.getFechaInicio() != null && ahora.isBefore(cupon.getFechaInicio())
                || cupon.getFechaFin() != null && ahora.isAfter(cupon.getFechaFin())) {
            return "El cupón no está dentro de su periodo de validez.";
        }
        if (cupon.getMaxUsos() != null
                && pedidoRepositorio.countUsosActivosDeCupon(cupon.getId()) >= cupon.getMaxUsos()) {
            return "El cupón ha alcanzado su máximo de usos.";
        }
        if (cupon.getImporteMinimo() != null && subtotal.compareTo(cupon.getImporteMinimo()) < 0) {
            return "El subtotal no alcanza el importe mínimo del cupón.";
        }
        if (cupon.getEmailCliente() != null && !cupon.getEmailCliente().isBlank()
                && (peticion.emailCliente() == null
                || !cupon.getEmailCliente().trim().equalsIgnoreCase(peticion.emailCliente().trim()))) {
            return "El cupón está asociado a otro cliente.";
        }
        return null;
    }

    private BigDecimal calcularDescuento(Cupon cupon, BigDecimal subtotal) {
        if (cupon.getValor() == null || cupon.getValor().signum() < 0) {
            throw new ReglaNegocioException("El cupón tiene un valor de descuento no válido.");
        }
        BigDecimal descuento = switch (cupon.getTipo() == null ? "" : cupon.getTipo().trim().toLowerCase()) {
            case "porcentaje", "porcentual", "percentage" ->
                    subtotal.multiply(cupon.getValor()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            case "fijo", "importe", "fixed" -> cupon.getValor();
            default -> throw new ReglaNegocioException("El tipo de descuento del cupón no es válido.");
        };
        return descuento.min(subtotal).setScale(2, RoundingMode.HALF_UP);
    }
}
