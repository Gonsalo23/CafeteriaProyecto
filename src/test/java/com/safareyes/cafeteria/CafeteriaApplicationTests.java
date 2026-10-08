package com.safareyes.cafeteria;

import com.safareyes.cafeteria.dtos.ProductoRanking;
import com.safareyes.cafeteria.dtos.VentasDiaDTO;
import com.safareyes.cafeteria.dtos.VentasHoraDTO;
import com.safareyes.cafeteria.modelos.Categoria;
import com.safareyes.cafeteria.modelos.Pedido;
import com.safareyes.cafeteria.modelos.Producto;
import com.safareyes.cafeteria.repositorios.ICategoriaRepositorio;
import com.safareyes.cafeteria.repositorios.IPedidoRepositorio;
import com.safareyes.cafeteria.repositorios.IProductoRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class CafeteriaApplicationTests {

    @Autowired
    private IProductoRepositorio iProductoRepositorio;
    @Autowired
    private IPedidoRepositorio iPedidoRepositorio;
    @Autowired
    private ICategoriaRepositorio iCategoriaRepositorio;

    @Test
    void contextLoads() {


        List<Producto> EndPoint1 = iProductoRepositorio.findByCategoria_IdAndActivoTrue(1);

        System.out.println(EndPoint1);

        List<ProductoRanking> EndPoint16 = iProductoRepositorio.rankingProductosMasVendidos();

        System.out.println(EndPoint16);

        List<VentasHoraDTO> resultado3 = iPedidoRepositorio.ventasPorHora();

        System.out.println(resultado3);

        LocalDateTime inicio = LocalDateTime.of(2026, 9,25,0,0);
        LocalDateTime fin = LocalDateTime.of(2026,9,26, 0, 0);


        VentasDiaDTO EndPoint15 = iPedidoRepositorio.ventasDia(inicio, fin);

        System.out.println(EndPoint15);

        Producto EndPoint2 = iProductoRepositorio.findProductoById(1);

        System.out.println(EndPoint2);

        List<Categoria> EndPoint7 = iCategoriaRepositorio.findAll();

        System.out.println(EndPoint7);

        Pedido EndPoint10 = iPedidoRepositorio.findById(1).orElse(null);

        System.out.println(EndPoint10);


        Pageable pageable = PageRequest.of(0, 10);

        Page<Pedido> EndPoint11 = iPedidoRepositorio.findByEstadoAndFechaBetween(
                "entregado",
                LocalDateTime.of(2026, 9, 1, 0, 0),
                LocalDateTime.of(2026, 10, 1, 0, 0),
                pageable


        );

        System.out.println(EndPoint11.getContent());
















    }

}
