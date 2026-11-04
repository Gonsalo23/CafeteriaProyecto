package com.safareyes.cafeteria.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record ProductoCreateDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        String descripcion,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor que 0")
        BigDecimal precio,

        @NotNull(message = "El ID de la categoria es obligatorio")
        Integer idCategoria,

        List<Integer> idAlergenos
) {}
