package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.ProductoCompraDTO;
import com.perfulandia.carritoservice.service.CarritoService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import com.perfulandia.carritoservice.assembler.CarritoAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.*;

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito", description = "Operaciones relacionadas con el carrito de compras")
@CrossOrigin("*")
public class CarritoController {

    @Autowired
    private final CarritoService carritoService;
    private final CarritoAssembler carritoAssembler;

    public CarritoController(CarritoService carritoService, CarritoAssembler carritoAssembler) {
        this.carritoService = carritoService;
        this.carritoAssembler = carritoAssembler;
    }

    @Operation(summary = "Crear un nuevo carrito")
    @PostMapping
    public EntityModel<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        Carrito nuevoCarrito = carritoService.crearCarrito(carrito.getIdCliente(), carrito.getDireccion());
        return carritoAssembler.toModel(nuevoCarrito);
    }
    //agregar producto al carrito
    @Operation(summary = "Agregar producto al carrito")
    @PostMapping("/{idCarrito}/productos")
    public EntityModel<Carrito> agregarProducto(
            @PathVariable("idCarrito") long idCarrito,
            @RequestBody ProductoCompraDTO productoCompraDTO) {
            Carrito carrito = carritoService.agregarProducto(idCarrito, productoCompraDTO);
            return carritoAssembler.toModel(carrito);
    }

    @Operation(summary = "Quitar producto del carrito")
    @DeleteMapping("/{idCarrito}/productos/{idProducto}")
    public EntityModel<Carrito> quitarProducto(
            @PathVariable("idCarrito") long idCarrito,
            @PathVariable("idProducto") long idProducto) {
                Carrito carrito = carritoService.quitarProducto(idCarrito, idProducto);
                return carritoAssembler.toModel(carrito);
    }

    @Operation(summary = "Obtener carrito por ID")
    @GetMapping("/{idCarrito}")
    public EntityModel<Carrito> obtenerCarrito(@PathVariable("idCarrito") long idCarrito) {
        Carrito carrito = carritoService.obtenerCarrito(idCarrito);
        return carritoAssembler.toModel(carrito);
    }


}
