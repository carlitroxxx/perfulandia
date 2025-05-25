package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.CarritoModel;
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
    public List<CarritoModel> obtenerCarrito() {
        return carritoService.getCarrito();
    }

    @PostMapping
    public CarritoModel agregarProducto(@RequestBody CarritoModel carritoItem) {
        return carritoService.addProducto(carritoItem);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable long id) {
        carritoService.eliminarProducto(id);
    }
}
