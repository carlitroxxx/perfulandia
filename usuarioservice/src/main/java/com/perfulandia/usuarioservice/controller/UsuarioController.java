package com.perfulandia.usuarioservice.controller;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import com.perfulandia.usuarioservice.assembler.UsuarioAssembler;

import java.util.List;

import org.springframework.hateoas.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioAssembler usuarioAssembler;
    //Constructor para poder consumir la interfaz
    public UsuarioController(UsuarioService service, UsuarioAssembler usuarioAssembler) {
        this.service = service;
        this.usuarioAssembler = usuarioAssembler;
    }

    @Operation(summary = "Listar usuarios")
    @GetMapping
    public CollectionModel<EntityModel<Usuario>> listar() {
        List<EntityModel<Usuario>> usuarios = service.listar().stream()
                .map(usuarioAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioController.class).listar()).withSelfRel()
        );
    }

    @Operation(summary = "Guardar usuario")
    @PostMapping
    public EntityModel<Usuario> guardar(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = service.guardar(usuario);
        return usuarioAssembler.toModel(nuevoUsuario);
    }

    @Operation(summary = "Buscar usuario por ID")
    @GetMapping("/{id}")
    public EntityModel<Usuario> buscar(@PathVariable long id) {
        Usuario usuario = service.buscar(id);
        return usuarioAssembler.toModel(usuario);
    }

    @Operation(summary = "Eliminar usuario")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id){
        service.eliminar(id);
    }



}
