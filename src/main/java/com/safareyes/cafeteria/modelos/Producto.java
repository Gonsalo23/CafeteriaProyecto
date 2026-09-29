package com.safareyes.cafeteria.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column(precision = 10, scale = 2)
    private BigDecimal precio;
    @Column
    private Boolean disponible;
    @Column
    private Boolean activo;






}
