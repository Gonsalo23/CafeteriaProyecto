package com.safareyes.cafeteria.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PedidoCreateDTO(
        @Positive(message = "El ID del cliente debe ser positivo")
        Integer clienteId,
        String codigoCupon,
        String dniSocio,
        @NotEmpty(message = "El pedido debe incluir al menos una línea")
        @Size(max = 15, message = "El pedido no puede tener más de 15 líneas")
        List<@NotNull @Valid LineaPedidoCreateDTO> lineas
) {
}
