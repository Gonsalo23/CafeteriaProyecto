package com.safareyes.cafeteria.controladores;

import com.safareyes.cafeteria.excepciones.RecursoNoEncontradoException;
import com.safareyes.cafeteria.excepciones.ReglaNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorController {
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail recursoNoEncontrado (RecursoNoEncontradoException e) {

        return construir(HttpStatus.NOT_FOUND, "El recurso no existe" , e.getMessage());

    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ProblemDetail reglaNegocio (ReglaNegocioException e) {

        return construir(HttpStatus.valueOf(422), "La regla de negocio no se cumple" , e.getMessage());

    }



    private ProblemDetail construir(HttpStatus status, String titulo, String detalle) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detalle);
        problemDetail.setTitle(titulo);
        problemDetail.setProperty("timestamp", java.time.Instant.now());
        return problemDetail;
    }

}
