package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.modelos.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ICuponRepositorio extends JpaRepository<Cupon, Integer> {



}
