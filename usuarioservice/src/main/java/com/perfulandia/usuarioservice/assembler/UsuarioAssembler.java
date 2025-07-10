package com.perfulandia.usuarioservice.assembler;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.controller.UsuarioController;

// importacion para envolver recursos con enlaces HATEOAS en las respuestas REST
import org.springframework.hateoas.EntityModel;
// importacion para transformar entidades en modelos HATEOAS con enlaces asociados
import org.springframework.hateoas.server.RepresentationModelAssembler;
// importacion para marcar clase gestionado por Spring
import org.springframework.stereotype.Component;
// importacion de métodos para crear links basados en MVC
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
// Convertir objetos (Usuarios) en EntityModel con enlaces HATEOAS
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                // Link que permite obtener este mismo usuario
                linkTo(methodOn(UsuarioController.class).buscar(usuario.getId())).withSelfRel(),
                // Link para obtener todos los usuarios
                linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"),
                // Link para crear un nuevo usuario mediante una solicitud POST
                linkTo(methodOn(UsuarioController.class).guardar(usuario)).withRel("crear").withType("POST"));
    }
}