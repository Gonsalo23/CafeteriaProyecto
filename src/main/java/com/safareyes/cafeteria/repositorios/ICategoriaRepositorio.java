package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.modelos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ICategoriaRepositorio extends JpaRepository<Categoria, Integer> {


    List<Categoria> findAll();
}
