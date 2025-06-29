package com.perfulandia.carritoservice.assembler;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.controller.CarritoController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CarritoAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {

    @Override
    public EntityModel<Carrito> toModel(Carrito carrito) {
        return EntityModel.of(carrito,
                linkTo(methodOn(CarritoController.class).crearCarrito(carrito)).withRel("carritos"),
                linkTo(methodOn(CarritoController.class).agregarProducto(carrito.getIdCarrito(),null)).withRel("agregarProducto"),
                linkTo(methodOn(CarritoController.class).quitarProducto(carrito.getIdCarrito(), 0L)).withRel("quitarProducto"),
                linkTo(methodOn(CarritoController.class).obtenerCarrito(carrito.getIdCarrito())).withSelfRel());
    }
}
