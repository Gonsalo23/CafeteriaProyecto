package com.safareyes.cafeteria.dtos.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.List;

public record ProductoResponseDTO(
        Integer id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Boolean disponible,
        Boolean activo,
        String categoriaNombre,
        List<String> alergenos
) {}
