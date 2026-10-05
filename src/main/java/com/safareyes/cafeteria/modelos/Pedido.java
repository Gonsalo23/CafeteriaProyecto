package com.safareyes.cafeteria.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero_turno")
    private Integer numeroTurno;

    private LocalDateTime fecha;

    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cupon", nullable = false)
    private Cupon cupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_descuento", nullable = false)
    private Descuento descuento;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "detalle_pedido",
            joinColumns = @JoinColumn(name = "id_pedido"),
            inverseJoinColumns = @JoinColumn(name = "id_producto")
    )
    private List<Producto> productos;




}
