package com.safareyes.cafeteria.controladores;

import com.safareyes.cafeteria.repositorios.ICategoriaRepositorio;
import com.safareyes.cafeteria.servicios.ICategoriaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final ICategoriaServicio categoriaServicio;

    @GetMapping
    public List<ICategoriaRepositorio.CategoriaResumen> listar() {
        return categoriaServicio.listarCategorias();
    }
}
