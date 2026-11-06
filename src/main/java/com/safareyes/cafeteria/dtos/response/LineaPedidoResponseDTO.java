package com.safareyes.cafeteria.dtos.response;

import java.math.BigDecimal;

public record LineaPedidoResponseDTO(
        Integer productoId,
        String productoNombre,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {
}
