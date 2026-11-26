package com.safareyes.cafeteria.dtos.request;

import com.safareyes.cafeteria.validaciones.CodigoCupon;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

import java.math.BigDecimal;

public record CuponValidarDTO(
        @NotBlank(message = "El código del cupón es obligatorio")
        @CodigoCupon
        String codigo,
        @NotNull(message = "El subtotal es obligatorio")
        @DecimalMin(value = "0.00", message = "El subtotal no puede ser negativo")
        @Digits(integer = 8, fraction = 2, message = "El subtotal admite hasta 2 decimales")
        BigDecimal subtotal,
        @Email(message = "El email del cliente no tiene un formato válido")
        String emailCliente
) {
}
