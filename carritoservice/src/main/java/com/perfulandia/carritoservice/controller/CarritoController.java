package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.ProductoCompraDTO;
import com.perfulandia.carritoservice.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    //crear carrito
    @PostMapping
    public Carrito crearCarrito(@RequestBody Carrito carrito) {
        return carritoService.crearCarrito(carrito.getIdCliente(),carrito.getDireccion());
    }
    @PostMapping("/{idCarrito}/productos")
    public Carrito agregarProducto(@PathVariable long idCarrito, @RequestBody ProductoCompraDTO productoCompraDTO) {
        return carritoService.agregarProducto(idCarrito, productoCompraDTO);
    }
    @DeleteMapping("/{idCarrito}/productos/{idProducto}")
    public Carrito quitarProducto(@PathVariable long idCarrito, @PathVariable long idProducto) {
        return carritoService.quitarProducto(idCarrito, idProducto);
    }
    @GetMapping("/{idCarrito}")
    public Carrito obtenerCarrito(@PathVariable long idCarrito) {
        return carritoService.obtenerCarrito(idCarrito);
    }


}
