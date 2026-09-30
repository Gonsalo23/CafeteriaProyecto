package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.modelos.Alergeno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IAlergenoRepositorio extends JpaRepository<Alergeno, Integer> {



}
