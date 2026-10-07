package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.dtos.VentasDiaDTO;
import com.safareyes.cafeteria.dtos.VentasHoraDTO;
import com.safareyes.cafeteria.modelos.Pedido;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface IPedidoRepositorio extends JpaRepository<Pedido, Integer> {

    @Query(nativeQuery = true, value = "select extract(hour from pe.fecha) as hora, sum(dp.cantidad * dp.precio) as ventas from pedido pe join detalle_pedido dp on dp.id_pedido = pe.id where pe.estado = 'entregado' group by hora order by hora")

    List<VentasHoraDTO> ventasPorHora();

    @Query(nativeQuery = true, value = "select count(distinct pe.id) as num_pedidos, sum(dp.cantidad * dp.precio) as total, sum(dp.cantidad * dp.precio) / 1.10 as base, sum(dp.cantidad * dp.precio) - sum(dp.cantidad * dp.precio) / 1.10 as iva from pedido pe join detalle_pedido dp on dp.id_pedido = pe.id where pe.estado = 'entregado' and pe.fecha >= :inicio and pe.fecha < :fin")
    VentasDiaDTO ventasDia(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin

            );

}
