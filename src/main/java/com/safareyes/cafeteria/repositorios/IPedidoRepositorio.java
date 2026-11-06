package com.safareyes.cafeteria.repositorios;

import com.safareyes.cafeteria.dtos.response.VentasDiaDTO;
import com.safareyes.cafeteria.dtos.response.VentasHoraDTO;
import com.safareyes.cafeteria.modelos.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository

public interface IPedidoRepositorio extends JpaRepository<Pedido, Integer>, JpaSpecificationExecutor<Pedido> {

    static Specification<Pedido> filtros(String estado, LocalDateTime desde, LocalDateTime hastaExclusivo) {
        Specification<Pedido> specification =
                (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (estado != null && !estado.isBlank()) {
            String estadoNormalizado = estado.trim().toLowerCase(Locale.ROOT);
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("estado")), estadoNormalizado));
        }

        if (desde != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), desde));
        }

        if (hastaExclusivo != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get("fecha"), hastaExclusivo));
        }

        return specification;
    }

    @Query(nativeQuery = true, value = "select extract(hour from pe.fecha) as hora, sum(dp.cantidad * dp.precio) as ventas from cafeteria.pedido pe join cafeteria.detalle_pedido dp on dp.id_pedido = pe.id where pe.estado = 'entregado' group by hora order by hora")

    List<VentasHoraDTO> ventasPorHora();

    @Query(nativeQuery = true, value = "select count(distinct pe.id) as num_pedidos, sum(dp.cantidad * dp.precio) as total, sum(dp.cantidad * dp.precio) / 1.10 as base, sum(dp.cantidad * dp.precio) - sum(dp.cantidad * dp.precio) / 1.10 as iva from cafeteria.pedido pe join cafeteria.detalle_pedido dp on dp.id_pedido = pe.id where pe.estado = 'entregado' and pe.fecha >= :inicio and pe.fecha < :fin")
    VentasDiaDTO ventasDia(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin

            );

    Optional<Pedido> findById(Integer id);

    @Query(value = """
            select dp.id_producto as "productoId",
                   p.nombre as "productoNombre",
                   dp.cantidad as cantidad,
                   dp.precio as "precioUnitario",
                   (dp.cantidad * dp.precio) as subtotal
            from cafeteria.detalle_pedido dp
            join cafeteria.producto p on p.id = dp.id_producto
            where dp.id_pedido = :pedidoId
            order by dp.id
            """, nativeQuery = true)
    List<LineaPedidoProjection> buscarLineasPorPedidoId(@Param("pedidoId") Integer pedidoId);

    interface LineaPedidoProjection {
        Integer getProductoId();
        String getProductoNombre();
        Integer getCantidad();
        java.math.BigDecimal getPrecioUnitario();
        java.math.BigDecimal getSubtotal();
    }

}
