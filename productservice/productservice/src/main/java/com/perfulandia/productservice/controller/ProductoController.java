package com.perfulandia.productservice.controller;

import com.perfulandia.productservice.assembler.ProductoAssembler;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import com.perfulandia.productservice.assembler.ProductoAssembler;

import java.util.List;

import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

//Nuevas importaciones DTO conexión al MS usuario
import org.springframework.web.client.RestTemplate;
//Para hacer peticiones HTTP a otros microservicios.


@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService servicio;
    private final RestTemplate restTemplate;
    private final ProductoAssembler productoAssembler;
    public ProductoController(ProductoService servicio,  RestTemplate restTemplate, ProductoAssembler productoAssembler) {
        this.servicio = servicio;
        this.restTemplate = restTemplate;
        this.productoAssembler = productoAssembler;
    }

    //listar
    @GetMapping
    public CollectionModel<EntityModel<Producto>> listar() {
        List<EntityModel<Producto>> productos = servicio.listar().stream()
                .map(productoAssembler::toModel)  // Llama a tu assembler para cada producto
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listar()).withSelfRel()
        );
    }

    //guardar
    @PostMapping
    public EntityModel<Producto> guardar(@RequestBody Producto producto) {
        Producto productoGuardado = servicio.guardar(producto);
        return productoAssembler.toModel(productoGuardado);
    }

    //buscar por id
    @GetMapping("/{id}")
    public EntityModel<Producto> buscar(@PathVariable long id) {
        Producto producto = servicio.bucarPorId(id);
        return productoAssembler.toModel(producto);
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id){
        servicio.eliminar(id);
    }

    //Nuevo método
    @GetMapping("/usuario/{id}")
    public Usuario obtenerUsuario(@PathVariable long id){
        return restTemplate.getForObject("http://localhost:8081/api/usuarios/"+id,Usuario.class);
    }
}
