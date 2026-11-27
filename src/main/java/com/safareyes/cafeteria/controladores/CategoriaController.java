package com.safareyes.cafeteria.controladores;

import com.safareyes.cafeteria.dtos.request.CategoriaDisponibilidadDTO;
import com.safareyes.cafeteria.repositorios.ICategoriaRepositorio;
import com.safareyes.cafeteria.servicios.ICategoriaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/categorias", "/categorias"})
@RequiredArgsConstructor
public class CategoriaController {

    private final ICategoriaServicio categoriaServicio;

    @GetMapping
    public List<ICategoriaRepositorio.CategoriaResumen> listar(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Boolean disponibilidad
    ) {
        return categoriaServicio.listarCategorias(id, disponibilidad);
    }

    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<Void> cambiarDisponibilidad(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaDisponibilidadDTO dto
    ) {
        categoriaServicio.cambiarDisponibilidadCategoria(id, dto.disponibilidad());
        return ResponseEntity.noContent().build();
    }
}
