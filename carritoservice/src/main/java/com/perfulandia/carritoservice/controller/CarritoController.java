package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.Carrito;
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
    public List<Carrito> obtenerCarrito() {
        return carritoService.listarCarritos();
    }

    @PostMapping
    public Carrito agregarProducto(@RequestBody Carrito carritoItem) {
        return carritoService.agregarProducto();
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable long id) {
        carritoService.eliminarProducto(id);
    }
}
