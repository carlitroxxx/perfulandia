package com.perfulandia.carritoservice.service;

import com.perfulandia.carritoservice.model.*;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoService {
    private final CarritoRepository repo;
    private final RestTemplate restTemplate;

    @Autowired
    public CarritoService(CarritoRepository repo,   RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }
    //Se crea el carrito del cliente
    public Carrito crearCarrito(long idCliente, String direccion){
        Carrito carrito = new Carrito();
        carrito.setIdCliente(idCliente);
        carrito.setDireccion(direccion);
        carrito.setProductoCompra(new ArrayList<>());
        return repo.save(carrito);
    }
    //Metodo para agregar productos al carrito
    public Carrito agregarProducto(long idCarrito, ProductoCompraDTO productoDTO){
        //Se debe ingresar el id del carrito y el producto a agregar
        Carrito carrito=repo.findById(idCarrito).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        //Obtener datos de producto
        ProductoDTO productoBD = restTemplate.getForObject("https://perfulandia-product.onrender.com//api/productos/{id}",ProductoDTO.class, productoDTO.getIdProducto());
        if (productoBD == null){throw new RuntimeException("No se encontro el producto");}

        ProductoCompra producto = new ProductoCompra();
        producto.setIdProducto(productoBD.getId());
        producto.setCantidad(productoDTO.getCantidad());
        producto.setPrecio(productoBD.getPrecio());
        producto.setSubTotal(productoBD.getPrecio()*productoDTO.getCantidad());
        producto.setCarrito(carrito);

        carrito.getProductoCompra().add(producto);
        return repo.save(carrito);
    }
    //Metodo para quitar productos del carrito, se necesita el id del carro y el id del producto
    public Carrito quitarProducto(long idCarrito, long idProducto){
        Carrito carrito = repo.findById(idCarrito).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        carrito.getProductoCompra().removeIf(producto->producto.getIdProducto()==idProducto);
        return repo.save(carrito);
    }
    //buscar el carrito por id
    public Carrito obtenerCarrito(long idCarrito){
        return repo.findById(idCarrito).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

    }



}