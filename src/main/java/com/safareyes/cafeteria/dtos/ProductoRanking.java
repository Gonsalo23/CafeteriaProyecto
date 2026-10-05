package com.safareyes.cafeteria.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoRanking {

    private String nombre;
    private Long totalVendido;
}
