package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IClienteRepositorio extends JpaRepository<Cliente, Integer> {
}
