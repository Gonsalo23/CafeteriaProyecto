package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.dtos.request.CuponCreateDTO;
import com.safareyes.cafeteria.dtos.request.CuponValidarDTO;
import com.safareyes.cafeteria.dtos.response.CuponResponseDTO;
import com.safareyes.cafeteria.dtos.response.CuponValidacionResponseDTO;

public interface ICuponServicio {
    CuponResponseDTO crearCupon(CuponCreateDTO peticion);
    CuponValidacionResponseDTO validarCupon(CuponValidarDTO peticion);
}
