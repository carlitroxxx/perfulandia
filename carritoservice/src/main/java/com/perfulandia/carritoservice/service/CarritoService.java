package com.perfulandia.carritoservice.service;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.ProductosCompra;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import com.perfulandia.carritoservice.model.ProductoCompraDTO;
import com.perfulandia.carritoservice.model.OrdenCompraDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CarritoService {
    private final CarritoRepository repo;

    private final RestTemplate restTemplate;

    public CarritoService(CarritoRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    public ProductosCompra agregarProducto(ProductosCompra producto) {
        producto.setSubTotal(producto.getCantidad()*producto.getPrecioUni());
        return repo.save(producto);
    }

    public void eliminarProducto(Long id) {
        repo.deleteById(id);
    }

    public String enviarOrdenCompra(Long idCliente, String direccion) {
        List<ProductosCompra> items = repo.findByIdCliente(idCliente);

        if (items.isEmpty()) {
            throw new RuntimeException("Carrito vacío para el cliente: " + idCliente);
        }

        OrdenCompraDTO orden = new OrdenCompraDTO();
        orden.setIdCliente(idCliente);
        orden.setDireccion(direccion);

        for (ProductosCompra item : items) {
            ProductoCompraDTO dto = new ProductoCompraDTO(
                    item.getIdProducto(),
                    item.getCantidad(),
                    item.getPrecio(),
                    item.getSubTotal()
            );
            orden.getProductos().add(dto);
        }

        //String pedidoUrl = "http://localhost:8082/api/pedidos"; // Ajusta el puerto según tu microservicio de pedidos
        //return restTemplate.postForObject(pedidoUrl, orden, String.class);
    }
}