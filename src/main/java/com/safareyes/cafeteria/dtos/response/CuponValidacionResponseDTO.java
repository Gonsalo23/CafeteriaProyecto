package com.safareyes.cafeteria.dtos.response;

import java.math.BigDecimal;

public record CuponValidacionResponseDTO(
        String codigo,
        boolean valido,
        BigDecimal descuentoAplicado,
        BigDecimal totalConDescuento,
        String mensaje
) {
}
