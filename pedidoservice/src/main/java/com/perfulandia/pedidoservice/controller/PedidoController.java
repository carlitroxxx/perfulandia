package com.perfulandia.pedidoservice.controller;
import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.service.PedidoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService servicio;
    public PedidoController(PedidoService servicio) {this.servicio = servicio;}

    //Listar pedidos
    @GetMapping
    public List<Pedido> listarPedidos(){return servicio.listarPedidos();}
    //Listar pedidos segun cliente
    @GetMapping("/cli/{id}")
    public List<Pedido> buscarPedidosPorId(@PathVariable long id){return servicio.listarPedidosPorId(id);}
    //Generar pedido
    @PostMapping
    public Pedido generarPedido(@RequestBody Pedido pedido){return servicio.generarPedido(pedido);}
    //Buscar pedido
    @GetMapping("/{id}")
    public Pedido buscarPedido(@PathVariable long id){return servicio.buscarPedido(id);}
    //Eliminar pedido
    @DeleteMapping("/{id}")
    public void eliminarPedido(@PathVariable long id){servicio.eliminarPedido(id);}

}
