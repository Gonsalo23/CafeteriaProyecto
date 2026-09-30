package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.modelos.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IDescuentoRepositorio extends JpaRepository<Descuento, Integer> {



}
