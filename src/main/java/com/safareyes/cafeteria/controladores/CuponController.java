package com.safareyes.cafeteria.controladores;

import com.safareyes.cafeteria.dtos.request.CuponCreateDTO;
import com.safareyes.cafeteria.dtos.request.CuponValidarDTO;
import com.safareyes.cafeteria.dtos.response.CuponResponseDTO;
import com.safareyes.cafeteria.dtos.response.CuponValidacionResponseDTO;
import com.safareyes.cafeteria.servicios.ICuponServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class CuponController {
    private final ICuponServicio cuponServicio;

    @PostMapping("/cupones")
    public ResponseEntity<CuponResponseDTO> crear(@Valid @RequestBody CuponCreateDTO peticion) {
        CuponResponseDTO creado = cuponServicio.crearCupon(peticion);
        var ubicacion = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/cupones/{id}")
                .buildAndExpand(creado.id())
                .toUri();
        return ResponseEntity.created(ubicacion).body(creado);
    }

    @PostMapping("/cupones/validar")
    public CuponValidacionResponseDTO validar(@Valid @RequestBody CuponValidarDTO peticion) {
        return cuponServicio.validarCupon(peticion);
    }
}
