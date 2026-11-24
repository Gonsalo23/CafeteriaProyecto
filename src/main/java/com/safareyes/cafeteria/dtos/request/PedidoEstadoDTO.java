package com.safareyes.cafeteria.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PedidoEstadoDTO(
        @NotBlank(message = "El estado es obligatorio")
        @Pattern(
                regexp = "pendiente|en_preparacion|listo|entregado|cancelado",
                message = "El estado debe ser pendiente, en_preparacion, listo, entregado o cancelado"
        )
        String estado
) {
}
