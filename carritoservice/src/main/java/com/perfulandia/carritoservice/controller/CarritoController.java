package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.CarritoItem;
import com.perfulandia.carritoservice.service.CarritoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping
    public List<CarritoItem> obtenerCarrito() {
        return carritoService.getCarrito();
    }

    @PostMapping
    public CarritoItem agregarProducto(@RequestBody CarritoItem carritoItem) {
        return carritoService.addProducto(carritoItem);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable long id) {
        carritoService.eliminarProducto(id);
    }
}
