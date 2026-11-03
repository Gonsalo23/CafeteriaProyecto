package com.safareyes.cafeteria.controladores;

import com.safareyes.cafeteria.dtos.request.ProductoCreateDTO;
import com.safareyes.cafeteria.dtos.request.ProductoUpdateDTO;
import com.safareyes.cafeteria.dtos.response.ProductoResponseDTO;
import com.safareyes.cafeteria.servicios.IProductoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final IProductoServicio productoServicio;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoServicio.crearProducto(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoUpdateDTO dto) {
        return ResponseEntity.ok(productoServicio.actualizarProducto(id, dto));
    }

    @PatchMapping("/{id}/disponibilidad")
    @PreAuthorize("hasAnyRole('ADMIN', 'BARISTA')")
    public ResponseEntity<Void> cambiarDisponibilidad(@PathVariable Long id, @Valid @RequestBody ProductoDisponibilidadDTO dto) {
        productoServicio.cambiarDisponibilidad(id, dto.disponible());
        return ResponseEntity.noContent().build();
    }




}
