package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IPedidoRepositorio extends JpaRepository<Pedido, Integer> {


}
