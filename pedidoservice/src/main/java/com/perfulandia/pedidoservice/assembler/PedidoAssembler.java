package com.perfulandia.pedidoservice.assembler;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.controller.PedidoController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                // Link a sí mismo: obtener este pedido
                linkTo(methodOn(PedidoController.class).buscarPedido(pedido.getId())).withSelfRel(),
                // Link a la lista completa de pedidos
                linkTo(methodOn(PedidoController.class).listarPedidos()).withRel("todosLosPedidos"),
                // Link para cambiar estado del pedido (por ejemplo)
                linkTo(methodOn(PedidoController.class).cambiarEstadoPedido(pedido.getId(), pedido.getEstado()))
                        .withRel("cambiarEstado"),
                // Link para eliminar pedido
                linkTo(methodOn(PedidoController.class).eliminarPedido(pedido.getId())).withRel("eliminarPedido"));
    }
}
