package com.perfulandia.usuarioservice.controller;
import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

//Test del controller
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UsuarioService usuarioService;

    //JSON A LIST Y LIST A JSON
    private final ObjectMapper objectMapper = new ObjectMapper();

    //test get
    @Test
    @DisplayName("Test Get usuarios")
    void testGetAll() throws Exception {
        when(usuarioService.listar()).thenReturn(List.of(new Usuario(1L, "Mayckol","mayckolmardones@gmail.com","CLIENTE")));
        // EJECUTAMOS Y VERIFICAMOS
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())//200
                .andExpect(jsonPath("$[0].nombre").value("Mayckol"));
        //Verificamos que se utilizó listar() de usuarioService
        verify(usuarioService, times(1)).listar();
    }

    //test post
    @Test
    @DisplayName("Test Post usuario")
    void testPost() throws Exception {
        //Usuario usuario = new Usuario(null,"Francisco","fra.vera@gmail.com","CLIENTE");
        Usuario usuario = Usuario.builder().nombre("Francisco").correo("fra.vera@gmail.com").rol("CLIENTE").build();

        when(usuarioService.guardar(any())).thenReturn(new Usuario(1L,"Francisco","fra.vera@gmail.com","CLIENTE"));
        mockMvc.perform(post("/api/usuarios")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Francisco"));
        //Verificamos que se utilizó guardar() de usuarioService
        verify(usuarioService, times(1)).guardar(any());
    }
    //test delete
    @Test
    @DisplayName("Test Delete")
    void testDelete() throws Exception {
        //simular delete del servicio
        doNothing().when(usuarioService).eliminar(1L);
        //Ejecutar solicitud con endpoint id 1
        mockMvc.perform(delete("/api/usuarios/1"))//200
            .andExpect(status().isOk());
        //Verificamos que se utilizó eliminar() de usuarioService
        verify(usuarioService, times(1)).eliminar(1L);

    }
}
