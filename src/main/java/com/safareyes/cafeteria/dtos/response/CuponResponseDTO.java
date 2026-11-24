package com.safareyes.cafeteria.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CuponResponseDTO(
        Integer id,
        String codigo,
        String tipo,
        BigDecimal valor,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Integer maxUsos,
        BigDecimal importeMinimo,
        String emailCliente,
        Boolean activo
) {
}
