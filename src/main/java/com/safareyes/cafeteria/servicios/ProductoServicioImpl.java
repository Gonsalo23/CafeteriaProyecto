package com.safareyes.cafeteria.servicios;

import com.safareyes.cafeteria.dtos.request.ProductoCreateDTO;
import com.safareyes.cafeteria.dtos.request.ProductoUpdateDTO;
import com.safareyes.cafeteria.dtos.response.ProductoResponseDTO;
import com.safareyes.cafeteria.excepciones.RecursoNoEncontradoException;
import com.safareyes.cafeteria.mappers.IProductoMapper;
import com.safareyes.cafeteria.modelos.Alergeno;
import com.safareyes.cafeteria.modelos.Categoria;
import com.safareyes.cafeteria.modelos.Producto;
import com.safareyes.cafeteria.repositorios.IAlergenoRepositorio;
import com.safareyes.cafeteria.repositorios.ICategoriaRepositorio;
import com.safareyes.cafeteria.repositorios.IProductoRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Service
public class ProductoServicioImpl implements IProductoServicio {

    private final IProductoRepositorio productoRepo;
    private final ICategoriaRepositorio categoriaRepo;
    private final IAlergenoRepositorio alergenoRepo;
    private final IProductoMapper productoMapper;

    public ProductoServicioImpl(IProductoRepositorio productoRepo,
                                ICategoriaRepositorio categoriaRepo,
                                IAlergenoRepositorio alergenoRepo,
                                IProductoMapper productoMapper) {
        this.productoRepo = productoRepo;
        this.categoriaRepo = categoriaRepo;
        this.alergenoRepo = alergenoRepo;
        this.productoMapper = productoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoResponseDTO> listarProductos(
            Integer categoriaId,
            String nombre,
            BigDecimal precioMaximo,
            Boolean disponible,
            Integer excluirAlergenoId,
            Pageable pageable
    ) {
        return productoRepo.findAll(
                IProductoRepositorio.filtros(categoriaId, nombre, precioMaximo, disponible, excluirAlergenoId),
                pageable
        ).map(productoMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public ProductoResponseDTO crearProducto(ProductoCreateDTO dto) {
        Categoria cat = categoriaRepo.findById(dto.idCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + dto.idCategoria()));

        Producto producto = productoMapper.toEntity(dto);
        producto.setCategoria(cat);

        if (dto.idAlergenos() != null && !dto.idAlergenos().isEmpty()) {
            List<Alergeno> alergenos = alergenoRepo.findAllById(dto.idAlergenos());
            producto.setAlergenos(alergenos);
        }

        return productoMapper.toResponseDTO(productoRepo.save(producto));
    }

    @Override
    @Transactional
    public ProductoResponseDTO actualizarProducto(Integer id, ProductoUpdateDTO dto) {
        Producto producto = productoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));

        Categoria cat = categoriaRepo.findById(dto.idCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + dto.idCategoria()));

        productoMapper.updateEntityFromDTO(dto, producto);
        producto.setCategoria(cat);

        if (dto.idsAlergenos() != null) {
            List<Alergeno> alergenos = alergenoRepo.findAllById(dto.idsAlergenos());
            producto.setAlergenos(alergenos);
        }

        return productoMapper.toResponseDTO(productoRepo.save(producto));
    }

    @Override
    @Transactional
    public void cambiarDisponibilidad(Integer id, Boolean disponible) {
        Producto producto = productoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));
        producto.setDisponible(disponible);
        productoRepo.save(producto);
    }

    @Override
    @Transactional
    public void bajaLogica(Integer id) {
        Producto producto = productoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));
        producto.setActivo(false);
        productoRepo.save(producto);
    }

}
