package com.perfulandia.pedidoservice.controller;
import com.perfulandia.pedidoservice.model.*;
import com.perfulandia.pedidoservice.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import com.perfulandia.pedidoservice.assembler.PedidoAssembler;

import java.util.List;

import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gestión de pedidos")
@CrossOrigin("*")
public class PedidoController {

    private final PedidoService servicio;

    public PedidoController(PedidoService servicio) {
    private final PedidoAssembler assembler;
    public PedidoController(PedidoService servicio, PedidoAssembler assembler) {
        this.servicio = servicio;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todos los pedidos")
    @GetMapping
    public List<Pedido> listarPedidos() {
        return servicio.listarPedidos();
    }

    @Operation(summary = "Listar pedidos por cliente")
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
    public List<Pedido> buscarPedidosPorId(@PathVariable long id) {
        return servicio.listarPedidosPorId(id);
    }

    @Operation(summary = "Buscar pedido por ID")
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
    public Pedido buscarPedido(@PathVariable long id) {
        return servicio.buscarPedido(id);
    }

    @Operation(summary = "Eliminar pedido")
    public EntityModel<Pedido> buscarPedido(@PathVariable long id) {
        Pedido pedido = servicio.buscarPedido(id);
        return assembler.toModel(pedido);
    }

    //Eliminar pedido
    @DeleteMapping("/{id}")
    public String eliminarPedido(@PathVariable long id) {
        servicio.eliminarPedido(id);
        return "Pedido ID: " + id + " eliminado";
    }

    @Operation(summary = "Generar pedido desde carrito")
    @PostMapping("/{id}")
    public Pedido generarPedido(@PathVariable long id) {
        return servicio.recibirOrden(id);
    public EntityModel<Pedido> generarPedido(@PathVariable long id) {
        Pedido pedido = servicio.recibirOrden(id);
        return assembler.toModel(pedido);
    }

    @Operation(summary = "Cambiar estado del pedido")
    @PutMapping("/{id}/estado")
    public Pedido cambiarEstadoPedido(@PathVariable long id, @RequestParam EstadoPedido estado) {
        return servicio.cambiarEstadoPedido(id, estado);
    public EntityModel<Pedido> cambiarEstadoPedido(@PathVariable long id, @RequestParam EstadoPedido estado) {
        Pedido pedido = servicio.cambiarEstadoPedido(id, estado);
        return assembler.toModel(pedido);
    }

    //PUT /api/pedidos/1/estado?estado=ENVIADO
}
