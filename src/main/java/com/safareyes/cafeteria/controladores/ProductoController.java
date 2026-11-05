package com.safareyes.cafeteria.controladores;

import com.safareyes.cafeteria.dtos.request.ProductoCreateDTO;
import com.safareyes.cafeteria.dtos.request.ProductoDisponibilidadDTO;
import com.safareyes.cafeteria.dtos.request.ProductoUpdateDTO;
import com.safareyes.cafeteria.dtos.response.ProductoResponseDTO;
import com.safareyes.cafeteria.servicios.IProductoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final IProductoServicio productoServicio;

    @GetMapping
    public Page<ProductoResponseDTO> listar(
            @RequestParam(required = false) Integer categoriaId,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) BigDecimal precioMaximo,
            @RequestParam(required = false) Boolean disponible,
            @RequestParam(required = false) Integer excluirAlergenoId,
            @PageableDefault(size = 10, sort = "nombre") Pageable pageable
    ) {
        return productoServicio.listarProductos(
                categoriaId, nombre, precioMaximo, disponible, excluirAlergenoId, pageable
        );
    }

    @GetMapping("/{id}")
    public ProductoResponseDTO buscarPorId(@PathVariable Integer id) {
        return productoServicio.buscarProductoPorId(id);
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoServicio.crearProducto(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoUpdateDTO dto) {
        return ResponseEntity.ok(productoServicio.actualizarProducto(id, dto));
    }

    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<Void> cambiarDisponibilidad(@PathVariable Integer id, @Valid @RequestBody ProductoDisponibilidadDTO dto) {
        productoServicio.cambiarDisponibilidad(id, dto.disponible());
        return ResponseEntity.noContent().build();
    }




}
