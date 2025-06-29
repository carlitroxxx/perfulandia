package com.perfulandia.productservice.controller;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestión de productos")
public class ProductoController {

    private final ProductoService servicio;
    private final RestTemplate restTemplate;

    public ProductoController(ProductoService servicio, RestTemplate restTemplate) {
        this.servicio = servicio;
        this.restTemplate = restTemplate;
    }

    @Operation(summary = "Listar productos")
    @GetMapping
    public List<Producto> listar() {
        return servicio.listar();
    }

    @Operation(summary = "Guardar producto")
    @PostMapping
    public Producto guardar(@RequestBody Producto producto) {
        return servicio.guardar(producto);
    }

    @Operation(summary = "Buscar producto por ID")
    @GetMapping("/{id}")
    public Producto buscar(@PathVariable long id) {
        return servicio.bucarPorId(id);
    }

    @Operation(summary = "Eliminar producto")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id) {
        servicio.eliminar(id);
    }

    @Operation(summary = "Obtener usuario relacionado")
    @GetMapping("/usuario/{id}")
    public Usuario obtenerUsuario(@PathVariable long id) {
        return restTemplate.getForObject("http://localhost:8081/api/usuarios/" + id, Usuario.class);
    }
}
