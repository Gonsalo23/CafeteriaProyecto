package com.safareyes.cafeteria.mappers;

import com.safareyes.cafeteria.dtos.request.CuponCreateDTO;
import com.safareyes.cafeteria.dtos.response.CuponResponseDTO;
import com.safareyes.cafeteria.dtos.response.CuponValidacionResponseDTO;
import com.safareyes.cafeteria.modelos.Cupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ICuponMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Cupon toEntity(CuponCreateDTO peticion);

    CuponResponseDTO toResponse(Cupon cupon);

    @Mapping(target = "codigo", source = "cupon.codigo")
    @Mapping(target = "valido", source = "valido")
    @Mapping(target = "descuentoAplicado", source = "descuento")
    @Mapping(target = "totalConDescuento", source = "total")
    @Mapping(target = "mensaje", source = "mensaje")
    CuponValidacionResponseDTO toValidacionResponse(
            Cupon cupon,
            boolean valido,
            BigDecimal descuento,
            BigDecimal total,
            String mensaje
    );
}
