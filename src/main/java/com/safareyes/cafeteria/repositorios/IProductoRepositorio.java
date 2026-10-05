package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.dtos.ProductoRanking;
import com.safareyes.cafeteria.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository


public interface IProductoRepositorio extends JpaRepository<Producto, Integer> {
    // EndPoint7
    public List<Producto> findByCategoria_IdAndActivoTrue(Integer categoriaId);


    @Query(nativeQuery = true, value = ("select p.nombre, sum(dp.cantidad) as total_vendido from producto p join detalle_pedido dp on dp.id_producto = p.id join pedido pe on pe.id = dp.id_pedido where pe.estado = 'entregado' group by p.id, p.nombre order by total_vendido desc limit 5;"))
    List<ProductoRanking> rankingProductosMasVendidos();


}






