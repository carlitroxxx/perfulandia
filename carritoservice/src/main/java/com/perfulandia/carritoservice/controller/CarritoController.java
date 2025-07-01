package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.ProductoCompraDTO;
import com.perfulandia.carritoservice.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito", description = "Operaciones relacionadas con el carrito de compras")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Operation(summary = "Crear un nuevo carrito")
    @PostMapping
    public Carrito crearCarrito(@RequestBody Carrito carrito) {
        return carritoService.crearCarrito(carrito.getIdCliente(), carrito.getDireccion());
    }

    @Operation(summary = "Agregar producto al carrito")
    @PostMapping("/{idCarrito}/productos")
    public Carrito agregarProducto(
            @PathVariable("idCarrito") long idCarrito,
            @RequestBody ProductoCompraDTO productoCompraDTO) {
        return carritoService.agregarProducto(idCarrito, productoCompraDTO);
    }

    @Operation(summary = "Quitar producto del carrito")
    @DeleteMapping("/{idCarrito}/productos/{idProducto}")
    public Carrito quitarProducto(
            @PathVariable("idCarrito") long idCarrito,
            @PathVariable("idProducto") long idProducto) {
        return carritoService.quitarProducto(idCarrito, idProducto);
    }

    @Operation(summary = "Obtener carrito por ID")
    @GetMapping("/{idCarrito}")
    public Carrito obtenerCarrito(@PathVariable("idCarrito") long idCarrito) {
        return carritoService.obtenerCarrito(idCarrito);
    }


}
