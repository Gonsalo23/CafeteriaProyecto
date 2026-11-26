package com.safareyes.cafeteria.validaciones;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CodigoCuponValidator implements ConstraintValidator<CodigoCupon, String> {

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext contexto) {
        if (valor == null || valor.isBlank()) {
            return true;
        }
        return valor.trim().matches("[A-Za-z0-9-]+");
    }
}
