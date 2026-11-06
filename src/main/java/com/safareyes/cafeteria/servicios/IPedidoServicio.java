package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.dtos.response.PedidoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface IPedidoServicio {
    Page<PedidoResponseDTO> listarPedidos(String estado, LocalDate desde, LocalDate hasta, Pageable pageable);
}
