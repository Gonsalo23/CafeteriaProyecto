package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.dtos.response.CategoriaResponseDTO;

import java.util.List;

public interface ICategoriaServicio {
    List<CategoriaResponseDTO> listarCategorias();
}
