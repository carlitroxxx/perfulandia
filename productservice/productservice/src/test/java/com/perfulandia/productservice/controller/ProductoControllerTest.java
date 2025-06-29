package com.perfulandia.productservice.controller;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.service.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProductoService productoService;
    @MockitoBean
    private RestTemplate restTemplate;

    //Mapper
    private final ObjectMapper objectMapper = new ObjectMapper();
    Producto producto = new Producto(1L,"Sauvage Eau de Parfum 100ml",173990,10);
    @Test
    @DisplayName("Test GET productos")
    void testGetAll() throws Exception {
        when(productoService.listar()).thenReturn(List.of(new Producto(1L,"Sauvage Eau de Parfum 100ml",173990,10)));
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Sauvage Eau de Parfum 100ml"))
                .andExpect(jsonPath("$[0].precio").value(173990));
        verify(productoService, times(1)).listar();
    }

    @Test
    @DisplayName("Test POST productos")
    void testPost() throws Exception {
        Producto producto = Producto.builder().nombre("Sauvage Eau de Parfum 100ml").precio(173990).stock(10).build();

        when(productoService.guardar(any())).thenReturn(new Producto(1L,"Sauvage Eau de Parfum 100ml",173990,10));
        mockMvc.perform(post("/api/productos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sauvage Eau de Parfum 100ml"));
        verify(productoService, times(1)).guardar(any());
    }

    @Test
    @DisplayName("Test GET producto por id")
    void testGetId() throws Exception {
        when(productoService.bucarPorId(1L)).thenReturn(new Producto(1L,"Sauvage Eau de Parfum 100ml",173990,10));
        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sauvage Eau de Parfum 100ml"))
                .andExpect(jsonPath("$.precio").value(173990));

        verify(productoService, times(1)).bucarPorId(1L);
    }

    @Test
    @DisplayName("Test DELETE")
    void testEliminarProducto() throws Exception {
        doNothing().when(productoService).eliminar(1L);
        mockMvc.perform(delete("/api/productos/1")).andExpect(status().isOk());
        verify(productoService, times(1)).eliminar(1L);
    }

    @Test
    @DisplayName("Test GET usuario desde productoservice")
    void testObtenerUsuario() throws Exception {
        when(restTemplate.getForObject("http://localhost:8081/api/usuarios/1", Usuario.class))
                .thenReturn(new Usuario(1L, "Carlos", "carlosmoil@gmail.com", "CLIENTE"));

        mockMvc.perform(get("/api/productos/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.correo").value("carlosmoil@gmail.com"));
    }
}