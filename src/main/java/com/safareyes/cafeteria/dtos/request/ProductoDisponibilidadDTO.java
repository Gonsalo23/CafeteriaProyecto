package com.safareyes.cafeteria.dtos.request;

import jakarta.validation.constraints.NotNull;

public record ProductoDisponibilidadDTO(
        @NotNull(message = "El estado de disponibilidad es obligatorio")
        Boolean disponible
) {}

