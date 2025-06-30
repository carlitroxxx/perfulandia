package com.perfulandia.usuarioservice.assembler;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.controller.UsuarioController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).buscar(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"),
                linkTo(methodOn(UsuarioController.class).guardar(usuario)).withRel("crear").withType("POST"));
    }
}