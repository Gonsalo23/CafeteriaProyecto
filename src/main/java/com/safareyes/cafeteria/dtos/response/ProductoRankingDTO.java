package com.safareyes.cafeteria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoRankingDTO {

    private String nombre;
    private Long totalVendido;
}
