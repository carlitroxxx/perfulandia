package com.perfulandia.pedidoservice.controller;

import com.perfulandia.pedidoservice.model.*;
import com.perfulandia.pedidoservice.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import com.perfulandia.pedidoservice.assembler.PedidoAssembler;

import java.util.List;

import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin("*")
public class PedidoController {

    private final PedidoService servicio;
    private final PedidoAssembler assembler;
    public PedidoController(PedidoService servicio, PedidoAssembler assembler) {
        this.servicio = servicio;
        this.assembler = assembler;
    }

    //Listar pedidos
    @GetMapping
    public CollectionModel<EntityModel<Pedido>> listarPedidos() {
        List<EntityModel<Pedido>> pedidos = servicio.listarPedidos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoController.class).listarPedidos()).withSelfRel()
        );
    }

    //Listar pedidos segun cliente
    @GetMapping("/cli/{id}")
    public CollectionModel<EntityModel<Pedido>> buscarPedidosPorId(@PathVariable long id) {
        List<EntityModel<Pedido>> pedidos = servicio.listarPedidosPorId(id).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoController.class).buscarPedidosPorId(id)).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("todosLosPedidos")
        );
    }

    //Buscar pedido
    @GetMapping("/{id}")
    public EntityModel<Pedido> buscarPedido(@PathVariable long id) {
        Pedido pedido = servicio.buscarPedido(id);
        return assembler.toModel(pedido);
    }

    //Eliminar pedido
    @DeleteMapping("/{id}")
    public String eliminarPedido(@PathVariable long id){servicio.eliminarPedido(id); return "Pedido ID: "+id+" eliminado";}

    //Metodo para recibir Orden de compra desde Carrito y generar un pedido
    @PostMapping("/{id}")
    public EntityModel<Pedido> generarPedido(@PathVariable long id) {
        Pedido pedido = servicio.recibirOrden(id);
        return assembler.toModel(pedido);
    }

    //CONTROLAR ESTADOS DE pedido
    @PutMapping("/{id}/estado")
    public EntityModel<Pedido> cambiarEstadoPedido(@PathVariable long id, @RequestParam EstadoPedido estado) {
        Pedido pedido = servicio.cambiarEstadoPedido(id, estado);
        return assembler.toModel(pedido);
    }

    //PUT /api/pedidos/1/estado?estado=ENVIADO
}
