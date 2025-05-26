package com.perfulandia.pedidoservice.service;

import com.perfulandia.pedidoservice.model.*;
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
    public PedidoService(PedidoRepository repo) {
        this.repo = repo;
        this.restTemplate = new RestTemplate();

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
        OrdenCompraDTO orden = restTemplate.getForObject("http://localhost:8084/api/carrito/"+idCarrito, OrdenCompraDTO.class);
        //excepcion cuando no encuentra carrito...
        if (orden == null){throw new RuntimeException("No se encontró el carrito ID:"+idCarrito);}
        //crear un pedido
        Pedido pedido = Pedido.builder()
                .fechaPedido(LocalDate.now())
                .estado(EstadoPedido.GENERADO)
                .idCliente(orden.getIdCliente())
                .direccion(orden.getDireccion())
                .build();
        //Obtengo los productos del DTO y los guardo en un objeto local
        List<ProductoCompra> productos = new ArrayList<>();
        if(productos == null){productos = new ArrayList<>();}
        for (ProductoCompraDTO dto: orden.getProductoCompra()){
            ProductoCompra producto = new ProductoCompra();
            producto.setIdProducto(dto.getIdProducto());
            producto.setCantidad(dto.getCantidad());
            producto.setPrecio(dto.getPrecio());
            producto.setSubtotal(dto.getSubtotal());
            producto.setPedido(pedido);
            productos.add(producto);
        }
        pedido.setProductos(productos);
        Pedido pedidoGuardado = repo.save(pedido);


        return pedidoGuardado;
    }
    //METODO PARA CAMBIAR ESTADO DEL PEDIDO
    public Pedido cambiarEstadoPedido(long idPedido, EstadoPedido estado){
        Pedido pedido = buscarPedido(idPedido);
        if(pedido == null){throw new RuntimeException("Pedido no encontrado");}
        EstadoPedido estadoActual = pedido.getEstado();

        pedido.setEstado(estado);
        return repo.save(pedido);
    }
}
