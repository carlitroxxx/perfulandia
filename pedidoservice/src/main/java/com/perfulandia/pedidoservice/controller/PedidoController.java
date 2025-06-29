package com.perfulandia.pedidoservice.controller;

import com.perfulandia.pedidoservice.model.EstadoPedido;
import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gestión de pedidos")
public class PedidoController {

    private final PedidoService servicio;

    public PedidoController(PedidoService servicio) {
        this.servicio = servicio;
    }

    @Operation(summary = "Listar todos los pedidos")
    @GetMapping
    public List<Pedido> listarPedidos() {
        return servicio.listarPedidos();
    }

    @Operation(summary = "Listar pedidos por cliente")
    @GetMapping("/cli/{id}")
    public List<Pedido> buscarPedidosPorId(@PathVariable long id) {
        return servicio.listarPedidosPorId(id);
    }

    @Operation(summary = "Buscar pedido por ID")
    @GetMapping("/{id}")
    public Pedido buscarPedido(@PathVariable long id) {
        return servicio.buscarPedido(id);
    }

    @Operation(summary = "Eliminar pedido")
    @DeleteMapping("/{id}")
    public String eliminarPedido(@PathVariable long id) {
        servicio.eliminarPedido(id);
        return "Pedido ID: " + id + " eliminado";
    }

    @Operation(summary = "Generar pedido desde carrito")
    @PostMapping("/{id}")
    public Pedido generarPedido(@PathVariable long id) {
        return servicio.recibirOrden(id);
    }

    @Operation(summary = "Cambiar estado del pedido")
    @PutMapping("/{id}/estado")
    public Pedido cambiarEstadoPedido(@PathVariable long id, @RequestParam EstadoPedido estado) {
        return servicio.cambiarEstadoPedido(id, estado);
    }
}
