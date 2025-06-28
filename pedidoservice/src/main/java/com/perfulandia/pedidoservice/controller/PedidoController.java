package com.perfulandia.pedidoservice.controller;
import com.perfulandia.pedidoservice.model.*;
import com.perfulandia.pedidoservice.service.PedidoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService servicio;
    public PedidoController(PedidoService servicio) {
        this.servicio = servicio;
    }

    //Listar pedidos
    @GetMapping
    public List<Pedido> listarPedidos(){return servicio.listarPedidos();}
    //Listar pedidos segun cliente
    @GetMapping("/cli/{id}")
    public List<Pedido> buscarPedidosPorId(@PathVariable long id){return servicio.listarPedidosPorId(id);}
    //Buscar pedido
    @GetMapping("/{id}")
    public Pedido buscarPedido(@PathVariable long id){return servicio.buscarPedido(id);}
    //Eliminar pedido
    @DeleteMapping("/{id}")
    public String eliminarPedido(@PathVariable long id){servicio.eliminarPedido(id); return "Pedido ID: "+id+" eliminado";}

    //Metodo para recibir Orden de compra desde Carrito y generar un pedido
    @PostMapping("/{id}")
    public Pedido generarPedido(@PathVariable long id){
        return servicio.recibirOrden(id);
    }

    //CONTROLAR ESTADOS DE pedido
    @PutMapping("/{id}/estado")
    public Pedido cambiarEstadoPedido(@PathVariable long id, @RequestParam EstadoPedido estado){
        return servicio.cambiarEstadoPedido(id, estado);
    }
    //PUT /api/pedidos/1/estado?estado=ENVIADO



}
