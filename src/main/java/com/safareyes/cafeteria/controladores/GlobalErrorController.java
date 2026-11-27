package com.safareyes.cafeteria.controladores;

import com.safareyes.cafeteria.excepciones.RecursoNoEncontradoException;
import com.safareyes.cafeteria.excepciones.ReglaNegocioException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalErrorController {
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail recursoNoEncontrado(
            RecursoNoEncontradoException e,
            HttpServletRequest request
    ) {
        return construir(HttpStatus.NOT_FOUND, "El recurso no existe", e.getMessage(), request);
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ProblemDetail reglaNegocio(
            ReglaNegocioException e,
            HttpServletRequest request
    ) {
        return construir(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "La regla de negocio no se cumple",
                e.getMessage(),
                request
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail respuestaEstado(
            ResponseStatusException e,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                e.getStatusCode(),
                e.getReason() == null ? "La solicitud no es válida." : e.getReason()
        );
        problemDetail.setTitle("Solicitud incorrecta");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail argumentosNoValidos(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        String detalle = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage() == null
                        ? "El campo " + error.getField() + " no es válido."
                        : error.getDefaultMessage())
                .distinct()
                .collect(Collectors.joining(" "));
        return construir(HttpStatus.BAD_REQUEST, "Solicitud incorrecta", detalle, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail cuerpoNoLegible(HttpServletRequest request) {
        return construir(
                HttpStatus.BAD_REQUEST,
                "Solicitud incorrecta",
                "El JSON enviado está mal formado o contiene valores con un tipo incorrecto.",
                request
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail tipoParametroIncorrecto(
            MethodArgumentTypeMismatchException e,
            HttpServletRequest request
    ) {
        String tipoEsperado = e.getRequiredType() == null
                ? "un valor válido"
                : e.getRequiredType().getSimpleName();
        String detalle = "El parámetro '" + e.getName()
                + "' debe tener un valor de tipo " + tipoEsperado + ".";
        if (Boolean.class.equals(e.getRequiredType()) || boolean.class.equals(e.getRequiredType())) {
            detalle = "El parámetro '" + e.getName() + "' debe ser true o false.";
        }
        return construir(HttpStatus.BAD_REQUEST, "Solicitud incorrecta", detalle, request);
    }

    private ProblemDetail construir(
            HttpStatus status,
            String titulo,
            String detalle,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detalle);
        problemDetail.setTitle(titulo);
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
