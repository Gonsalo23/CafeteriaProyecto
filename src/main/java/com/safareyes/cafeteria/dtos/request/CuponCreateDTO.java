package com.safareyes.cafeteria.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import com.safareyes.cafeteria.validaciones.CodigoCupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CuponCreateDTO(
        @NotBlank(message = "El código del cupón es obligatorio")
        @Size(max = 50, message = "El código no puede superar los 50 caracteres")
        @CodigoCupon
        String codigo,

        @NotBlank(message = "El tipo de descuento es obligatorio")
        @Pattern(regexp = "(?i)porcentaje|fijo", message = "El tipo debe ser porcentaje o fijo")
        String tipo,

        @NotNull(message = "El valor del descuento es obligatorio")
        @DecimalMin(value = "0.01", message = "El valor debe ser mayor que cero")
        @Digits(integer = 6, fraction = 2, message = "El valor admite hasta 6 enteros y 2 decimales")
        BigDecimal valor,

        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDateTime fechaInicio,

        @NotNull(message = "La fecha de fin es obligatoria")
        LocalDateTime fechaFin,

        @NotNull(message = "El máximo de usos es obligatorio")
        @Positive(message = "El máximo de usos debe ser mayor que cero")
        Integer maxUsos,

        @DecimalMin(value = "0.00", message = "El importe mínimo no puede ser negativo")
        @Digits(integer = 6, fraction = 2, message = "El importe mínimo admite hasta 6 enteros y 2 decimales")
        BigDecimal importeMinimo,

        @Email(message = "El email del cliente no tiene un formato válido")
        @Size(max = 150, message = "El email no puede superar los 150 caracteres")
        String emailCliente
) {
}
