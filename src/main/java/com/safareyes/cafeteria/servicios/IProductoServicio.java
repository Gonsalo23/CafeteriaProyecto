package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.dtos.request.ProductoCreateDTO;
import com.safareyes.cafeteria.dtos.request.ProductoUpdateDTO;
import com.safareyes.cafeteria.dtos.response.ProductoResponseDTO;

public interface IProductoServicio {
    ProductoResponseDTO crearProducto(ProductoCreateDTO dto); // Recibe CreateDTO, devuelve ResponseDTO
    ProductoResponseDTO actualizarProducto(Integer id, ProductoUpdateDTO dto);
    void cambiarDisponibilidad(Integer id, Boolean disponible);
    void bajaLogica(Integer id);
}