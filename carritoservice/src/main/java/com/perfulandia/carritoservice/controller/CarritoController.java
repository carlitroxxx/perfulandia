package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.ProductoCompraDTO;
import com.perfulandia.carritoservice.service.CarritoService;
import org.springframework.beans.factory.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import com.perfulandia.carritoservice.assembler.CarritoAssembler;

import java.util.List;

import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin("*")
public class CarritoController {
    @Autowired
    private final CarritoService carritoService;
    private final CarritoAssembler carritoAssembler;

    public CarritoController(CarritoService carritoService, CarritoAssembler carritoAssembler) {
        this.carritoService = carritoService;
        this.carritoAssembler = carritoAssembler;
    }

    //crear carrito
    @PostMapping
    public EntityModel<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        Carrito nuevoCarrito = carritoService.crearCarrito(carrito.getIdCliente(), carrito.getDireccion());
        return carritoAssembler.toModel(nuevoCarrito);
    }

    //agregar producto al carrito
    @PostMapping("/{idCarrito}/productos")
    public EntityModel<Carrito> agregarProducto(@PathVariable long idCarrito, @RequestBody ProductoCompraDTO productoCompraDTO) {
        Carrito carrito = carritoService.agregarProducto(idCarrito, productoCompraDTO);
        return carritoAssembler.toModel(carrito);
    }

    //quitar productos del carrito
    @DeleteMapping("/{idCarrito}/productos/{idProducto}")
    public EntityModel<Carrito> quitarProducto(@PathVariable long idCarrito, @PathVariable long idProducto) {
        Carrito carrito = carritoService.quitarProducto(idCarrito, idProducto);
        return carritoAssembler.toModel(carrito);
    }

    //obtener carrito por su id
    @GetMapping("/{idCarrito}")
    public EntityModel<Carrito> obtenerCarrito(@PathVariable long idCarrito) {
        Carrito carrito = carritoService.obtenerCarrito(idCarrito);
        return carritoAssembler.toModel(carrito);
    }

}