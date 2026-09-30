package com.safareyes.cafeteria;

import com.safareyes.cafeteria.modelos.Producto;
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

    @Test
    void contextLoads() {


        List<Producto> resultado1 = iProductoRepositorio.findByCategoria_IdAndActivoTrue(1);


        System.out.println(resultado1);





    }

}
