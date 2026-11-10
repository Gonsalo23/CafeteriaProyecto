package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.dtos.response.PedidoResponseDTO;
import com.safareyes.cafeteria.dtos.response.LineaPedidoResponseDTO;
import com.safareyes.cafeteria.dtos.response.ProductoRankingDTO;
import com.safareyes.cafeteria.dtos.response.VentasDiaDTO;
import com.safareyes.cafeteria.excepciones.RecursoNoEncontradoException;
import com.safareyes.cafeteria.mappers.IPedidoMapper;
import com.safareyes.cafeteria.repositorios.IPedidoRepositorio;
import com.safareyes.cafeteria.repositorios.IProductoRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PedidoServicioImpl implements IPedidoServicio {

    private final IPedidoRepositorio pedidoRepo;
    private final IProductoRepositorio productoRepo;
    private final IPedidoMapper pedidoMapper;

    public PedidoServicioImpl(
            IPedidoRepositorio pedidoRepo,
            IProductoRepositorio productoRepo,
            IPedidoMapper pedidoMapper
    ) {
        this.pedidoRepo = pedidoRepo;
        this.productoRepo = productoRepo;
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
}
