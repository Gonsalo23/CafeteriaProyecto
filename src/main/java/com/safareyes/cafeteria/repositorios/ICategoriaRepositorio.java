package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.dtos.response.CategoriaResponseDTO;
import com.safareyes.cafeteria.modelos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ICategoriaRepositorio extends JpaRepository<Categoria, Integer> {

    @Query("""
            select new com.safareyes.cafeteria.dtos.response.CategoriaResponseDTO(
                c.id,
                c.nombre,
                c.disponibilidad,
                (select count(p) from Producto p where p.categoria = c and p.activo = true)
            )
            from Categoria c
            order by c.nombre
            """)
    List<CategoriaResponseDTO> listarConCantidadProductosActivos();
}
