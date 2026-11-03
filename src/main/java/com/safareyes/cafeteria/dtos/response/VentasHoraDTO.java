package com.safareyes.cafeteria.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
public class VentasHoraDTO {

    private BigDecimal hora;
    private BigDecimal ventas;
}
