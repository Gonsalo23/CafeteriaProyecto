package com.safareyes.cafeteria;

import com.safareyes.cafeteria.dtos.ProductoRanking;
import com.safareyes.cafeteria.dtos.VentasHoraDTO;
import com.safareyes.cafeteria.modelos.Producto;
import com.safareyes.cafeteria.repositorios.IPedidoRepositorio;
import com.safareyes.cafeteria.repositorios.IProductoRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;



import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class CafeteriaApplicationTests {

    @Autowired
    private IProductoRepositorio iProductoRepositorio;
    @Autowired
    private IPedidoRepositorio iPedidoRepositorio;

    @Test
    void contextLoads() {


        List<Producto> EndPoint7 = iProductoRepositorio.findByCategoria_IdAndActivoTrue(1);

        System.out.println(EndPoint7);

        List<ProductoRanking> EndPoint16 = iProductoRepositorio.rankingProductosMasVendidos();

        System.out.println(EndPoint16);

        List<VentasHoraDTO> resultado3 = iPedidoRepositorio.ventasPorHora();

        System.out.println(resultado3);












    }

}
