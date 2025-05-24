package com.perfulandia.pedidoservice.service;

import com.perfulandia.pedidoservice.model.OrdenCompraDTO;
import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.model.ProductoCompra;
import com.perfulandia.pedidoservice.model.ProductoCompraDTO;
import com.perfulandia.pedidoservice.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    public final PedidoRepository repo;
    private final RestTemplate restTemplate;
    public PedidoService(PedidoRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;

    }

    //Lista con todos los pedidos
    public List<Pedido> listarPedidos() {return repo.findAll();}
    //Lista con pedidos segun cliente
    public List<Pedido> listarPedidosPorId(long idCliente){return repo.findByIdCliente(idCliente);}
    //Buscar pedido
    public Pedido buscarPedido(long id){return repo.findById(id).orElse(null);}
    //Eliminar pedido
    public void eliminarPedido(long id){repo.deleteById(id);}


    // Metodo para crear Pedido con datos desde Microservicio Carrito usando id carrito
    public Pedido recibirOrden(long idCarrito){
        OrdenCompraDTO orden = restTemplate.getForObject("http://localhost:8080/api/carritos/"+idCarrito, OrdenCompraDTO.class);
        //excepcion cuando no encuentra carrito...
        if (orden == null){
            throw new RuntimeException("No se encontró el carrito ID:"+idCarrito);
        }
        //crear un pedido
        Pedido pedido = Pedido.builder()
                .fechaPedido(LocalDate.now())
                .estado("PENDIENTE")
                .idCliente(orden.getIdCliente())
                .direccion(orden.getDireccion())
                .build();
        //Obtengo los productos del DTO y los guardo en un objeto local
        List<ProductoCompra> productos = new ArrayList<>();
        for (ProductoCompraDTO dto: orden.getProductos()){
            ProductoCompra producto = new ProductoCompra();
            producto.setIdProducto(dto.getIdProducto());
            producto.setCantidad(dto.getCantidad());
            producto.setPrecio(dto.getPrecio());
            producto.setSubtotal(dto.getSubtotal());
            producto.setPedido(pedido);
            productos.add(producto);
        }
        return repo.save(pedido);
    }
}
