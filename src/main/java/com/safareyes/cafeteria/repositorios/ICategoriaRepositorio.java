package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.modelos.Categoria;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ICategoriaRepositorio extends JpaRepository<Categoria, Integer> {

    @Query("""
            select c.id as id,
                   c.nombre as nombre,
                   c.disponibilidad as disponibilidad,
                   (select count(p) from Producto p where p.categoria = c and p.activo = true)
                       as productosActivos
            from Categoria c
            where (:id is null or c.id = :id)
              and (:disponibilidad is null or c.disponibilidad = :disponibilidad)
            order by c.nombre
            """)
    List<CategoriaResumen> listarConCantidadProductosActivos(
            @Param("id") Integer id,
            @Param("disponibilidad") Boolean disponibilidad
    );

    interface CategoriaResumen {
        Integer getId();
        String getNombre();
        Boolean getDisponibilidad();
        Long getProductosActivos();
    }
}
