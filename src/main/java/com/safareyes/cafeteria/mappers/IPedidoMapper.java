package com.safareyes.cafeteria.mappers;

import com.safareyes.cafeteria.dtos.response.LineaPedidoResponseDTO;
import com.safareyes.cafeteria.dtos.response.PedidoResponseDTO;
import com.safareyes.cafeteria.modelos.Pedido;
import com.safareyes.cafeteria.repositorios.IPedidoRepositorio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPedidoMapper {

    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "lineas", ignore = true)
    PedidoResponseDTO toResponseDTO(Pedido pedido);

    List<PedidoResponseDTO> toResponseDTO(List<Pedido> pedidos);

    LineaPedidoResponseDTO toResponseDTO(IPedidoRepositorio.LineaPedidoProjection linea);

    List<LineaPedidoResponseDTO> toLineaResponseDTO(List<IPedidoRepositorio.LineaPedidoProjection> lineas);

    @Mapping(target = "clienteId", source = "pedido.cliente.id")
    @Mapping(target = "lineas", source = "lineas")
    PedidoResponseDTO toResponseDTO(
            Pedido pedido,
            List<LineaPedidoResponseDTO> lineas
    );
}
