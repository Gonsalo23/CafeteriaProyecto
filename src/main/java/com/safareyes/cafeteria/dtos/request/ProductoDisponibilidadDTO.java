package com.safareyes.cafeteria.dtos.request;

import tools.jackson.databind.JsonNode;

public record ProductoDisponibilidadDTO(
        JsonNode disponible
) {}
