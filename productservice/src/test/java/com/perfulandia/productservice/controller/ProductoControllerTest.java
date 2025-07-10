package com.perfulandia.productservice.controller;

import com.perfulandia.productservice.assembler.ProductoAssembler;
import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.service.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @MockitoBean
    private RestTemplate restTemplate;

    @MockitoBean
    private ProductoAssembler productoAssembler;

    private Producto crearProductoEjemplo() {
        return new Producto(1L, "Sauvage Eau de Parfum 100ml", 173990, 10);
    }

    @Test
    @DisplayName("Test GET Listar Productos")
    void testListarProductos() throws Exception {
        Producto producto = crearProductoEjemplo();
        when(productoService.listar()).thenReturn(List.of(producto));
        when(productoAssembler.toModel(producto)).thenReturn(EntityModel.of(producto));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.productoList[0].id").value(1L))
                .andExpect(jsonPath("$._embedded.productoList[0].nombre").value("Sauvage Eau de Parfum 100ml"))
                .andExpect(jsonPath("$._embedded.productoList[0].precio").value(173990));

        verify(productoService, times(1)).listar();
    }

    @Test
    @DisplayName("Test POST Crear Producto")
    void testCrearProducto() throws Exception {
        Producto producto = crearProductoEjemplo();
        when(productoService.guardar(any(Producto.class))).thenReturn(producto);
        when(productoAssembler.toModel(producto)).thenReturn(EntityModel.of(producto));

        mockMvc.perform(post("/api/productos")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Sauvage Eau de Parfum 100ml\",\"precio\":173990,\"stock\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sauvage Eau de Parfum 100ml"))
                .andExpect(jsonPath("$.precio").value(173990));

        verify(productoService, times(1)).guardar(any(Producto.class));
    }

    @Test
    @DisplayName("Test GET Buscar Producto por ID")
    void testBuscarProducto() throws Exception {
        Producto producto = crearProductoEjemplo();
        when(productoService.bucarPorId(1L)).thenReturn(producto);
        when(productoAssembler.toModel(producto)).thenReturn(EntityModel.of(producto));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Sauvage Eau de Parfum 100ml"));

        verify(productoService, times(1)).bucarPorId(1L);
    }

    @Test
    @DisplayName("Test DELETE Eliminar Producto")
    void testEliminarProducto() throws Exception {
        doNothing().when(productoService).eliminar(1L);

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isOk());

        verify(productoService, times(1)).eliminar(1L);
    }

    @Test
    @DisplayName("Test GET Obtener Usuario Relacionado")
    void testObtenerUsuario() throws Exception {
        Usuario usuario = new Usuario(1L, "Carlos", "carlosmoil@gmail.com", "CLIENTE");
        when(restTemplate.getForObject("http://localhost:8081/api/usuarios/1", Usuario.class))
                .thenReturn(usuario);

        mockMvc.perform(get("/api/productos/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.correo").value("carlosmoil@gmail.com"));

        verify(restTemplate, times(1))
                .getForObject("http://localhost:8081/api/usuarios/1", Usuario.class);
    }
}