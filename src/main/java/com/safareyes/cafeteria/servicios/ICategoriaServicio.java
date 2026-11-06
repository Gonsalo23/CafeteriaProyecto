package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.repositorios.ICategoriaRepositorio;

import java.util.List;

public interface ICategoriaServicio {
    List<ICategoriaRepositorio.CategoriaResumen> listarCategorias();
}
