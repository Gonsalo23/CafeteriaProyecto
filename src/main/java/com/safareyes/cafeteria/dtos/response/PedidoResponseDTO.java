package com.safareyes.cafeteria.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PedidoResponseDTO(
        Integer id,
        Integer numeroTurno,
        LocalDateTime fecha,
        String estado,
        Integer clienteId,
        List<LineaPedidoResponseDTO> lineas
) {
}
