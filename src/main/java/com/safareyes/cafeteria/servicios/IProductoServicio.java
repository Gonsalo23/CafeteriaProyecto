package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.dtos.request.ProductoCreateDTO;
import com.safareyes.cafeteria.dtos.request.ProductoUpdateDTO;
import com.safareyes.cafeteria.dtos.response.ProductoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface IProductoServicio {
    Page<ProductoResponseDTO> listarProductos(
            Integer categoriaId,
            String nombre,
            BigDecimal precioMaximo,
            Boolean disponible,
            Integer excluirAlergenoId,
            Pageable pageable
    );
    ProductoResponseDTO crearProducto(ProductoCreateDTO dto); // Recibe CreateDTO, devuelve ResponseDTO
    ProductoResponseDTO actualizarProducto(Integer id, ProductoUpdateDTO dto);
    void cambiarDisponibilidad(Integer id, Boolean disponible);
    void bajaLogica(Integer id);
}