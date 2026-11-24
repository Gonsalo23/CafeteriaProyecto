package com.safareyes.cafeteria.dtos.request;

import jakarta.validation.constraints.NotNull;

public record CategoriaDisponibilidadDTO(
        @NotNull(message = "La disponibilidad es obligatoria")
        Boolean disponibilidad
) {}
