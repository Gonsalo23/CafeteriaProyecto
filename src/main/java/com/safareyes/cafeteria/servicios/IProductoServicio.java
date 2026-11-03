package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.dtos.request.ProductoUpdateDTO;
import com.safareyes.cafeteria.dtos.response.ProductoResponseDTO;

public interface IProductoServicio {
    ProductoResponseDTO crearProducto(ProductoResponseDTO dto);
    ProductoResponseDTO actualizarProducto(Long id, ProductoUpdateDTO dto);
    void cambiarDisponibilidad(Long id, Boolean disponible);
    void bajaLogica(Long id);
}
