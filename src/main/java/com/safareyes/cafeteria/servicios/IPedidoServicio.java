package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.dtos.request.PedidoCreateDTO;
import com.safareyes.cafeteria.dtos.request.PedidoEstadoDTO;
import com.safareyes.cafeteria.dtos.response.PedidoResponseDTO;
import com.safareyes.cafeteria.dtos.response.ProductoRankingDTO;
import com.safareyes.cafeteria.dtos.response.VentasDiaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface IPedidoServicio {
    Page<PedidoResponseDTO> listarPedidos(String estado, LocalDate desde, LocalDate hasta, Pageable pageable);
    PedidoResponseDTO buscarPedidoPorId(Integer id);
    PedidoResponseDTO crearPedido(PedidoCreateDTO peticion);
    PedidoResponseDTO cambiarEstado(Integer id, PedidoEstadoDTO peticion);
    VentasDiaDTO consultarVentasDia(LocalDate fecha);
    java.util.List<ProductoRankingDTO> consultarProductosMasVendidos(LocalDate desde, LocalDate hasta, int limite);
}
