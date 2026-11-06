package com.safareyes.cafeteria.dtos.response;

import java.time.LocalDateTime;

public record PedidoResponseDTO(
        Integer id,
        Integer numeroTurno,
        LocalDateTime fecha,
        String estado,
        Integer clienteId
) {
}
