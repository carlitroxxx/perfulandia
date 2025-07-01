package com.perfulandia.pedidoservice.controller;

import com.perfulandia.pedidoservice.model.*;
import com.perfulandia.pedidoservice.service.PedidoService;
import com.perfulandia.pedidoservice.assembler.PedidoAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gestión de pedidos")
@CrossOrigin("*")
public class PedidoController {

    private final PedidoService servicio;
    private final PedidoAssembler assembler;

    public PedidoController(PedidoService servicio, PedidoAssembler assembler) {
        this.servicio = servicio;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todos los pedidos")
    @GetMapping
    public CollectionModel<EntityModel<Pedido>> listarPedidos() {
        List<EntityModel<Pedido>> pedidos = servicio.listarPedidos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoController.class).listarPedidos()).withSelfRel()
        );
    }

    @Operation(summary = "Listar pedidos por cliente")
    @GetMapping("/cli/{id}")
    public CollectionModel<EntityModel<Pedido>> listarPedidosPorCliente(@PathVariable long id) {
        List<EntityModel<Pedido>> pedidos = servicio.listarPedidosPorId(id).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoController.class).listarPedidosPorCliente(id)).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("todos-los-pedidos")
        );
    }

    @Operation(summary = "Buscar pedido por ID")
    @GetMapping("/{id}")
    public EntityModel<Pedido> buscarPedido(@PathVariable long id) {
        Pedido pedido = servicio.buscarPedido(id);
        return assembler.toModel(pedido);
    }

    @Operation(summary = "Eliminar pedido")
    @DeleteMapping("/{id}")
    public EntityModel<String> eliminarPedido(@PathVariable long id) {
        servicio.eliminarPedido(id);
        String mensaje = "Pedido ID: " + id + " eliminado";
        return EntityModel.of(mensaje,
                linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("lista-pedidos")
        );
    }

    @Operation(summary = "Generar pedido desde carrito")
    @PostMapping("/{id}")
    public EntityModel<Pedido> generarPedido(@PathVariable long id) {
        Pedido pedido = servicio.recibirOrden(id);
        return assembler.toModel(pedido);
    }

    @Operation(summary = "Cambiar estado del pedido")
    @PutMapping("/{id}/estado")
    public EntityModel<Pedido> cambiarEstadoPedido(@PathVariable long id, @RequestParam EstadoPedido estado) {
        Pedido pedido = servicio.cambiarEstadoPedido(id, estado);
        return assembler.toModel(pedido);
    }
}