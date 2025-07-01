package com.perfulandia.usuarioservice.controller;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.service.UsuarioService;
import com.perfulandia.usuarioservice.assembler.UsuarioAssembler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private UsuarioAssembler usuarioAssembler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Usuario crearUsuarioEjemplo() {
        return new Usuario(1L, "Carlos", "carlosmoil@gmail.com", "CLIENTE");
    }

    @Test
    @DisplayName("Test GET Listar Usuarios")
    void testListarUsuarios() throws Exception {
        Usuario usuario = crearUsuarioEjemplo();
        when(usuarioService.listar()).thenReturn(List.of(usuario));
        when(usuarioAssembler.toModel(usuario)).thenReturn(EntityModel.of(usuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList[0].nombre").value("Carlos"))
                .andExpect(jsonPath("$._embedded.usuarioList[0].correo").value("carlosmoil@gmail.com"));

        verify(usuarioService, times(1)).listar();
    }

    @Test
    @DisplayName("Test POST Crear Usuario")
    void testCrearUsuario() throws Exception {
        Usuario usuario = crearUsuarioEjemplo();
        when(usuarioService.guardar(any(Usuario.class))).thenReturn(usuario);
        when(usuarioAssembler.toModel(usuario)).thenReturn(EntityModel.of(usuario));

        mockMvc.perform(post("/api/usuarios")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.correo").value("carlosmoil@gmail.com"));

        verify(usuarioService, times(1)).guardar(any(Usuario.class));
    }

    @Test
    @DisplayName("Test GET Buscar Usuario por ID")
    void testBuscarUsuario() throws Exception {
        Usuario usuario = crearUsuarioEjemplo();
        when(usuarioService.buscar(1L)).thenReturn(usuario);
        when(usuarioAssembler.toModel(usuario)).thenReturn(EntityModel.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Carlos"));

        verify(usuarioService, times(1)).buscar(1L);
    }

    @Test
    @DisplayName("Test DELETE Eliminar Usuario")
    void testEliminarUsuario() throws Exception {
        doNothing().when(usuarioService).eliminar(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).eliminar(1L);
    }
}