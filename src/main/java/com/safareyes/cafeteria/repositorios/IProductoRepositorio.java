package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.dtos.response.ProductoRankingDTO;
import com.safareyes.cafeteria.modelos.Producto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository


public interface IProductoRepositorio extends JpaRepository<Producto, Integer>, JpaSpecificationExecutor<Producto> {
    static Specification<Producto> filtros(
            Integer categoriaId,
            String nombre,
            BigDecimal precioMaximo,
            Boolean disponible,
            Integer excluirAlergenoId
    ) {
        Specification<Producto> specification =
                (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("activo"));

        if (categoriaId != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("categoria").get("id"), categoriaId));
        }

        if (nombre != null && !nombre.isBlank()) {
            String texto = "%" + nombre.trim().toLowerCase() + "%";
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), texto));
        }

        if (precioMaximo != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precioMaximo));
        }

        if (disponible != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("disponible"), disponible));
        }

        if (excluirAlergenoId != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                var subquery = query.subquery(Integer.class);
                var productoConAlergeno = subquery.from(Producto.class);
                var alergeno = productoConAlergeno.join("alergenos");
                subquery.select(productoConAlergeno.get("id"))
                        .where(criteriaBuilder.equal(alergeno.get("id"), excluirAlergenoId));
                return criteriaBuilder.not(root.get("id").in(subquery));
            });
        }

        return specification;
    }

    // EndPoint7
    public List<Producto> findByCategoria_IdAndActivoTrue(Integer categoriaId);


    @Query(nativeQuery = true, value = ("select p.nombre, sum(dp.cantidad) as total_vendido from producto p join detalle_pedido dp on dp.id_producto = p.id join pedido pe on pe.id = dp.id_pedido where pe.estado = 'entregado' group by p.id, p.nombre order by total_vendido desc limit 5;"))
    List<ProductoRankingDTO> rankingProductosMasVendidos();

    public Producto findProductoById(Integer id);


}


