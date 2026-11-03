package com.safareyes.cafeteria.mappers;

import com.safareyes.cafeteria.dtos.request.ProductoCreateDTO;
import com.safareyes.cafeteria.dtos.request.ProductoUpdateDTO;
import com.safareyes.cafeteria.dtos.response.ProductoResponseDTO;
import com.safareyes.cafeteria.modelos.Alergeno;
import com.safareyes.cafeteria.modelos.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IProductoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponible", constant = "true")
    @Mapping(target = "activo", constant = "true")
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "alergenos", ignore = true)
    Producto toEntity(ProductoCreateDTO dto);

    @Mapping(target = "categoriaNombre", source = "categoria.nombre")
    @Mapping(target = "alergenos", expression = "java(mapAlergenos(producto.getAlergenos()))")
    ProductoResponseDTO toResponseDTO(Producto producto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponible", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "alergenos", ignore = true)
    void updateEntityFromDTO(ProductoUpdateDTO dto, @MappingTarget Producto producto);

    default List<String> mapAlergenos(Set<Alergeno> alergenos) {
        if (alergenos == null) return List.of();
        return alergenos.stream().map(Alergeno::getNombre).collect(Collectors.toList());
    }

}
