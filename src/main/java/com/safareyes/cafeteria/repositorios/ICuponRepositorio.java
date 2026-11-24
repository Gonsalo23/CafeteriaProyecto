package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.modelos.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ICuponRepositorio extends JpaRepository<Cupon, Integer> {
    Optional<Cupon> findByCodigoIgnoreCase(String codigo);
    boolean existsByCodigoIgnoreCase(String codigo);
}
