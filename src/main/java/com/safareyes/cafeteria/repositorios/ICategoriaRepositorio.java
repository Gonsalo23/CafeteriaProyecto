package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.modelos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ICategoriaRepositorio extends JpaRepository<Categoria, Integer> {
}
