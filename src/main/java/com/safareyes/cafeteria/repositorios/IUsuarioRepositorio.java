package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IUsuarioRepositorio extends JpaRepository<Usuario, Integer> {
}
