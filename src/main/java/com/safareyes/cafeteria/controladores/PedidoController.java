package com.safareyes.cafeteria.controladores;

import com.safareyes.cafeteria.dtos.request.PedidoCreateDTO;
import com.safareyes.cafeteria.dtos.request.PedidoEstadoDTO;
import com.safareyes.cafeteria.dtos.response.PedidoResponseDTO;
import com.safareyes.cafeteria.dtos.response.ProductoRankingDTO;
import com.safareyes.cafeteria.dtos.response.VentasDiaDTO;
import com.safareyes.cafeteria.servicios.IPedidoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class PedidoController {

    private final IPedidoServicio pedidoServicio;

    @PostMapping("/pedidos")
    public ResponseEntity<PedidoResponseDTO> crear(@Valid @RequestBody PedidoCreateDTO peticion) {
        PedidoResponseDTO creado = pedidoServicio.crearPedido(peticion);
        var ubicacion = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/pedidos/{id}")
                .buildAndExpand(creado.id())
                .toUri();
        return ResponseEntity.created(ubicacion).body(creado);
    }

    @GetMapping("/pedidos")
    public Page<PedidoResponseDTO> listar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return pedidoServicio.listarPedidos(estado, desde, hasta, pageable);
    }

    @GetMapping("/pedidos/{id}")
    public PedidoResponseDTO buscarPorId(@PathVariable Integer id) {
        return pedidoServicio.buscarPedidoPorId(id);
    }

    @PatchMapping("/pedidos/{id}/estado")
    public PedidoResponseDTO cambiarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody PedidoEstadoDTO peticion
    ) {
        return pedidoServicio.cambiarEstado(id, peticion);
    }

    @GetMapping("/informes/ventas-dia")
    public VentasDiaDTO ventasDia(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        return pedidoServicio.consultarVentasDia(fecha);
    }

    @GetMapping("/informes/productos-ranking")
    public List<ProductoRankingDTO> productosMasVendidos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam @Min(1) int limite
    ) {
        return pedidoServicio.consultarProductosMasVendidos(desde, hasta, limite);
    }
}
