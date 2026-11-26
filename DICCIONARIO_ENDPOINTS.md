# Diccionario de endpoints

Referencia de los endpoints implementados y de los archivos Java relacionados.
Las rutas de esta lista reflejan las anotaciones actuales del código.

> **Prefijo de API:** el documento del proyecto puede indicar `/api/v1`, pero los
> controladores actuales no tienen ese prefijo. Por ejemplo, la ruta actual es
> `/productos`, no `/api/v1/productos`.

## Productos

### Endpoint 1 — Listar productos

- **HTTP:** `GET /productos`
- **Controlador:** [ProductoController.java](src/main/java/com/safareyes/cafeteria/controladores/ProductoController.java) — método `listar`.
- **Servicio:** [IProductoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IProductoServicio.java),
  [ProductoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/ProductoServicioImpl.java) — aplica filtros y paginación.
- **Entrada:** parámetros opcionales `categoriaId`, `nombre`, `precioMaximo`,
  `disponible`, `excluirAlergenoId` y paginación (`page`, `size`, `sort`).
- **Salida:** [ProductoResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/ProductoResponseDTO.java), dentro de una página.
- **Acceso a datos:** [IProductoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IProductoRepositorio.java) — filtros.
- **Mapeo/modelo:** [IProductoMapper.java](src/main/java/com/safareyes/cafeteria/mappers/IProductoMapper.java),
  [Producto.java](src/main/java/com/safareyes/cafeteria/modelos/Producto.java).

### Endpoint 2 — Consultar producto

- **HTTP:** `GET /productos/{id}`
- **Controlador:** [ProductoController.java](src/main/java/com/safareyes/cafeteria/controladores/ProductoController.java) — método `buscarPorId`.
- **Servicio:** [IProductoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IProductoServicio.java),
  [ProductoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/ProductoServicioImpl.java).
- **Salida:** [ProductoResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/ProductoResponseDTO.java).
- **Acceso a datos:** [IProductoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IProductoRepositorio.java).
- **Mapeo/modelo:** [IProductoMapper.java](src/main/java/com/safareyes/cafeteria/mappers/IProductoMapper.java), [Producto.java](src/main/java/com/safareyes/cafeteria/modelos/Producto.java).
- **Errores:** [RecursoNoEncontradoException.java](src/main/java/com/safareyes/cafeteria/excepciones/RecursoNoEncontradoException.java) si no existe o
  está dado de baja.

### Endpoint 3 — Crear producto

- **HTTP:** `POST /productos`
- **Controlador:** [ProductoController.java](src/main/java/com/safareyes/cafeteria/controladores/ProductoController.java) — método `crear`.
- **Entrada:** [ProductoCreateDTO.java](src/main/java/com/safareyes/cafeteria/dtos/request/ProductoCreateDTO.java).
- **Servicio:** [IProductoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IProductoServicio.java),
  [ProductoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/ProductoServicioImpl.java).
- **Salida:** [ProductoResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/ProductoResponseDTO.java) (`201 Created`).
- **Acceso a datos:** [IProductoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IProductoRepositorio.java),
  [ICategoriaRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/ICategoriaRepositorio.java),
  [IAlergenoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IAlergenoRepositorio.java).
- **Mapeo/modelos:** [IProductoMapper.java](src/main/java/com/safareyes/cafeteria/mappers/IProductoMapper.java), [Producto.java](src/main/java/com/safareyes/cafeteria/modelos/Producto.java),
  [Categoria.java](src/main/java/com/safareyes/cafeteria/modelos/Categoria.java), [Alergeno.java](src/main/java/com/safareyes/cafeteria/modelos/Alergeno.java).

### Endpoint 4 — Actualizar producto

- **HTTP:** `PUT /productos/{id}`
- **Controlador:** [ProductoController.java](src/main/java/com/safareyes/cafeteria/controladores/ProductoController.java) — método `actualizar`.
- **Entrada:** [ProductoUpdateDTO.java](src/main/java/com/safareyes/cafeteria/dtos/request/ProductoUpdateDTO.java).
- **Servicio:** [IProductoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IProductoServicio.java),
  [ProductoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/ProductoServicioImpl.java).
- **Salida:** [ProductoResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/ProductoResponseDTO.java).
- **Acceso a datos:** [IProductoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IProductoRepositorio.java),
  [ICategoriaRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/ICategoriaRepositorio.java),
  [IAlergenoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IAlergenoRepositorio.java).
- **Mapeo/modelos:** [IProductoMapper.java](src/main/java/com/safareyes/cafeteria/mappers/IProductoMapper.java), [Producto.java](src/main/java/com/safareyes/cafeteria/modelos/Producto.java),
  [Categoria.java](src/main/java/com/safareyes/cafeteria/modelos/Categoria.java), [Alergeno.java](src/main/java/com/safareyes/cafeteria/modelos/Alergeno.java).

### Endpoint 5 — Cambiar disponibilidad de producto

- **HTTP:** `PATCH /productos/{id}/disponibilidad`
- **Controlador:** [ProductoController.java](src/main/java/com/safareyes/cafeteria/controladores/ProductoController.java) —
  método `cambiarDisponibilidad`.
- **Entrada:** [ProductoDisponibilidadDTO.java](src/main/java/com/safareyes/cafeteria/dtos/request/ProductoDisponibilidadDTO.java), con el campo
  `disponible`.
- **Servicio:** [IProductoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IProductoServicio.java),
  [ProductoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/ProductoServicioImpl.java).
- **Acceso a datos/modelo:** [IProductoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IProductoRepositorio.java),
  [Producto.java](src/main/java/com/safareyes/cafeteria/modelos/Producto.java).
- **Respuesta:** `204 No Content`.

### Endpoint 6 — Dar de baja un producto

- **HTTP:** `PATCH /productos/{id}`
- **Controlador:** [ProductoController.java](src/main/java/com/safareyes/cafeteria/controladores/ProductoController.java) — método `darDeBaja`.
- **Servicio:** [IProductoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IProductoServicio.java),
  [ProductoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/ProductoServicioImpl.java) — método `bajaLogica`.
- **Acceso a datos/modelo:** [IProductoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IProductoRepositorio.java),
  [Producto.java](src/main/java/com/safareyes/cafeteria/modelos/Producto.java) — cambia `activo` a `false`; no elimina la fila.
- **Respuesta:** `204 No Content`. No requiere body.

## Categorías

### Endpoint 7 — Listar categorías

- **HTTP:** `GET /categorias`
- **Controlador:** [CategoriaController.java](src/main/java/com/safareyes/cafeteria/controladores/CategoriaController.java) — método `listar`.
- **Servicio:** [ICategoriaServicio.java](src/main/java/com/safareyes/cafeteria/servicios/ICategoriaServicio.java),
  [ProductoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/ProductoServicioImpl.java) — método `listarCategorias`.
- **Acceso a datos:** [ICategoriaRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/ICategoriaRepositorio.java) — consulta y
  proyección `CategoriaResumen`.
- **Modelo:** [Categoria.java](src/main/java/com/safareyes/cafeteria/modelos/Categoria.java).
- **Salida:** lista de categorías con el número de productos activos.

### Endpoint 8 — Cambiar disponibilidad de categoría

- **HTTP:** `PATCH /categorias/{id}/disponibilidad`
- **Controlador:** [CategoriaController.java](src/main/java/com/safareyes/cafeteria/controladores/CategoriaController.java) —
  método `cambiarDisponibilidad`.
- **Entrada:** [CategoriaDisponibilidadDTO.java](src/main/java/com/safareyes/cafeteria/dtos/request/CategoriaDisponibilidadDTO.java), con el campo
  `disponibilidad`.
- **Servicio:** [ICategoriaServicio.java](src/main/java/com/safareyes/cafeteria/servicios/ICategoriaServicio.java),
  [ProductoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/ProductoServicioImpl.java) — método
  `cambiarDisponibilidadCategoria`.
- **Acceso a datos/modelo:** [ICategoriaRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/ICategoriaRepositorio.java),
  [Categoria.java](src/main/java/com/safareyes/cafeteria/modelos/Categoria.java).
- **Respuesta:** `204 No Content`.

## Pedidos

### Endpoint 9 — Crear pedido

- **HTTP:** `POST /pedidos`
- **Controlador:** [PedidoController.java](src/main/java/com/safareyes/cafeteria/controladores/PedidoController.java) — método `crear`.
- **Entrada:** [PedidoCreateDTO.java](src/main/java/com/safareyes/cafeteria/dtos/request/PedidoCreateDTO.java),
  [LineaPedidoCreateDTO.java](src/main/java/com/safareyes/cafeteria/dtos/request/LineaPedidoCreateDTO.java).
- **Servicio:** [IPedidoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IPedidoServicio.java),
  [PedidoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/PedidoServicioImpl.java) — validaciones y creación.
- **Salida:** [PedidoResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/PedidoResponseDTO.java),
  [LineaPedidoResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/LineaPedidoResponseDTO.java) (`201 Created`).
- **Acceso a datos:** [IPedidoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IPedidoRepositorio.java),
  [IProductoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IProductoRepositorio.java), y según la petición,
  [ICuponRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/ICuponRepositorio.java), [IClienteRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IClienteRepositorio.java)
  o [IDescuentoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IDescuentoRepositorio.java).
- **Mapeo/modelos:** [IPedidoMapper.java](src/main/java/com/safareyes/cafeteria/mappers/IPedidoMapper.java), [Pedido.java](src/main/java/com/safareyes/cafeteria/modelos/Pedido.java),
  [DetallePedido.java](src/main/java/com/safareyes/cafeteria/modelos/DetallePedido.java), [Producto.java](src/main/java/com/safareyes/cafeteria/modelos/Producto.java).

### Endpoint 10 — Consultar pedido

- **HTTP:** `GET /pedidos/{id}`
- **Controlador:** [PedidoController.java](src/main/java/com/safareyes/cafeteria/controladores/PedidoController.java) — método `buscarPorId`.
- **Servicio:** [IPedidoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IPedidoServicio.java),
  [PedidoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/PedidoServicioImpl.java).
- **Salida:** [PedidoResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/PedidoResponseDTO.java) y
  [LineaPedidoResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/LineaPedidoResponseDTO.java).
- **Acceso a datos:** [IPedidoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IPedidoRepositorio.java).
- **Mapeo/modelos:** [IPedidoMapper.java](src/main/java/com/safareyes/cafeteria/mappers/IPedidoMapper.java), [Pedido.java](src/main/java/com/safareyes/cafeteria/modelos/Pedido.java),
  [DetallePedido.java](src/main/java/com/safareyes/cafeteria/modelos/DetallePedido.java).

### Endpoint 11 — Listar pedidos

- **HTTP:** `GET /pedidos`
- **Controlador:** [PedidoController.java](src/main/java/com/safareyes/cafeteria/controladores/PedidoController.java) — método `listar`.
- **Parámetros opcionales:** `estado`, `desde`, `hasta` y paginación.
- **Servicio:** [IPedidoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IPedidoServicio.java),
  [PedidoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/PedidoServicioImpl.java).
- **Salida:** página de [PedidoResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/PedidoResponseDTO.java).
- **Acceso a datos:** [IPedidoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IPedidoRepositorio.java).
- **Modelo:** [Pedido.java](src/main/java/com/safareyes/cafeteria/modelos/Pedido.java).

### Endpoint 12 — Cambiar estado del pedido

- **HTTP:** `PATCH /pedidos/{id}/estado`
- **Controlador:** [PedidoController.java](src/main/java/com/safareyes/cafeteria/controladores/PedidoController.java) —
  método `cambiarEstado`.
- **Entrada:** [PedidoEstadoDTO.java](src/main/java/com/safareyes/cafeteria/dtos/request/PedidoEstadoDTO.java).
- **Servicio:** [IPedidoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IPedidoServicio.java),
  [PedidoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/PedidoServicioImpl.java) — válida las transiciones permitidas.
- **Acceso a datos/modelo:** [IPedidoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IPedidoRepositorio.java),
  [Pedido.java](src/main/java/com/safareyes/cafeteria/modelos/Pedido.java).
- **Salida:** [PedidoResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/PedidoResponseDTO.java).

## Cupones

### Endpoint 13 — Crear cupón

- **HTTP:** `POST /cupones`
- **Controlador:** [CuponController.java](src/main/java/com/safareyes/cafeteria/controladores/CuponController.java) — método `crear`.
- **Entrada:** [CuponCreateDTO.java](src/main/java/com/safareyes/cafeteria/dtos/request/CuponCreateDTO.java).
- **Servicio:** [ICuponServicio.java](src/main/java/com/safareyes/cafeteria/servicios/ICuponServicio.java),
  [CuponServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/CuponServicioImpl.java).
- **Salida:** [CuponResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/CuponResponseDTO.java) (`201 Created`).
- **Acceso a datos/mapeo/modelo:** [ICuponRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/ICuponRepositorio.java),
  [ICuponMapper.java](src/main/java/com/safareyes/cafeteria/mappers/ICuponMapper.java), [Cupon.java](src/main/java/com/safareyes/cafeteria/modelos/Cupon.java).

### Endpoint 14 — Validar cupón

- **HTTP:** `POST /cupones/validar`
- **Controlador:** [CuponController.java](src/main/java/com/safareyes/cafeteria/controladores/CuponController.java) — método `validar`.
- **Entrada:** [CuponValidarDTO.java](src/main/java/com/safareyes/cafeteria/dtos/request/CuponValidarDTO.java).
- **Servicio:** [ICuponServicio.java](src/main/java/com/safareyes/cafeteria/servicios/ICuponServicio.java),
  [CuponServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/CuponServicioImpl.java) — comprueba vigencia, estado, usos y
  condiciones del cupón.
- **Salida:** [CuponValidacionResponseDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/CuponValidacionResponseDTO.java).
- **Acceso a datos/mapeo/modelos:** [ICuponRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/ICuponRepositorio.java),
  [IPedidoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IPedidoRepositorio.java), [ICuponMapper.java](src/main/java/com/safareyes/cafeteria/mappers/ICuponMapper.java),
  [Cupon.java](src/main/java/com/safareyes/cafeteria/modelos/Cupon.java), [Pedido.java](src/main/java/com/safareyes/cafeteria/modelos/Pedido.java).

## Informes

### Endpoint 15 — Consultar ventas de un día

- **HTTP:** `GET /informes/ventas-dia?fecha=AAAA-MM-DD`
- **Controlador:** [PedidoController.java](src/main/java/com/safareyes/cafeteria/controladores/PedidoController.java) — método `ventasDia`.
- **Servicio:** [IPedidoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IPedidoServicio.java),
  [PedidoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/PedidoServicioImpl.java).
- **Salida:** [VentasDiaDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/VentasDiaDTO.java).
- **Acceso a datos:** [IPedidoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IPedidoRepositorio.java).
- **Modelo relacionado:** [Pedido.java](src/main/java/com/safareyes/cafeteria/modelos/Pedido.java), [DetallePedido.java](src/main/java/com/safareyes/cafeteria/modelos/DetallePedido.java).

### Endpoint 16 — Ranking de productos vendidos

- **HTTP:** `GET /informes/productos-ranking?desde=AAAA-MM-DD&hasta=AAAA-MM-DD&limite=N`
- **Controlador:** [PedidoController.java](src/main/java/com/safareyes/cafeteria/controladores/PedidoController.java) —
  método `productosMasVendidos`.
- **Servicio:** [IPedidoServicio.java](src/main/java/com/safareyes/cafeteria/servicios/IPedidoServicio.java),
  [PedidoServicioImpl.java](src/main/java/com/safareyes/cafeteria/servicios/PedidoServicioImpl.java).
- **Salida:** lista de [ProductoRankingDTO.java](src/main/java/com/safareyes/cafeteria/dtos/response/ProductoRankingDTO.java).
- **Acceso a datos:** [IPedidoRepositorio.java](src/main/java/com/safareyes/cafeteria/repositorios/IPedidoRepositorio.java).
- **Modelos relacionados:** [Pedido.java](src/main/java/com/safareyes/cafeteria/modelos/Pedido.java), [DetallePedido.java](src/main/java/com/safareyes/cafeteria/modelos/DetallePedido.java),
  [Producto.java](src/main/java/com/safareyes/cafeteria/modelos/Producto.java).

## Archivos transversales

- [RecursoNoEncontradoException.java](src/main/java/com/safareyes/cafeteria/excepciones/RecursoNoEncontradoException.java) — respuesta de recurso no
  encontrado.
- [ConflictoException.java](src/main/java/com/safareyes/cafeteria/excepciones/ConflictoException.java) — conflictos, por ejemplo un código de
  cupón duplicado.
- [ReglaNegocioException.java](src/main/java/com/safareyes/cafeteria/excepciones/ReglaNegocioException.java) — reglas de negocio incumplidas.
- [BDD](BDD.sql) — definición SQL de tablas y datos iniciales; los cambios en este
  archivo no modifican automáticamente una base de datos PostgreSQL ya creada.

`IntegracionController.java` está vacío actualmente y no expone endpoints.
