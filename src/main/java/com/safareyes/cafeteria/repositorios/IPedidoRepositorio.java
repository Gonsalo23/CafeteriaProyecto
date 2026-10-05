package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.dtos.VentasHoraDTO;
import com.safareyes.cafeteria.modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface IPedidoRepositorio extends JpaRepository<Pedido, Integer> {

    @Query(nativeQuery = true, value = "select extract(hour from pe.fecha) as hora, sum(dp.cantidad * dp.precio) as ventas from pedido pe join detalle_pedido dp on dp.id_pedido = pe.id where pe.estado = 'entregado' group by hora order by hora")

    List<VentasHoraDTO> ventasPorHora();

}
