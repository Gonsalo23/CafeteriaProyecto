package com.safareyes.cafeteria.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class VentasDiaDTO {

    private Long numPedidos;
    private BigDecimal total;
    private BigDecimal base;
    private BigDecimal iva;

}
