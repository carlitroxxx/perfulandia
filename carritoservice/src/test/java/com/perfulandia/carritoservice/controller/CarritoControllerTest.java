package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.*;
import com.perfulandia.carritoservice.service.CarritoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarritoController.class)
public class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarritoService carritoService;

    // Mapper para convertir objetos a JSON y viceversa
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Datos de prueba
    private final Carrito carrito = new Carrito(1L, 100L, "Egaña 123", new ArrayList<>());
    private final ProductoCompraDTO productoDTO = new ProductoCompraDTO(10L, 2, 50, 100);

    @Test
    @DisplayName("Test POST crear carrito")
    void testCrearCarrito() throws Exception {
        // Configurar mock
        when(carritoService.crearCarrito(anyLong(), anyString()))
                .thenReturn(carrito);

        // Ejecutar y verificar
        mockMvc.perform(post("/api/carrito")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(carrito)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1L))
                .andExpect(jsonPath("$.idCliente").value(100L));

        // Verificar que se llamó al servicio
        verify(carritoService, times(1))
                .crearCarrito(anyLong(), anyString());
    }

    @Test
    @DisplayName("Test POST agregar producto al carrito")
    void testAgregarProducto() throws Exception {
        // Configurar mock
        Carrito carritoConProducto = new Carrito(1L, 100L, "Egaña 123",
                List.of(new ProductoCompra(1L, 10L, 2, 50, 100, null)));

        when(carritoService.agregarProducto(anyLong(), any(ProductoCompraDTO.class)))
                .thenReturn(carritoConProducto);

        // Ejecutar y verificar
        mockMvc.perform(post("/api/carrito/1/productos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoCompra[0].idProducto").value(10L))
                .andExpect(jsonPath("$.productoCompra[0].subTotal").value(100));

        // Verificar que se llamó al servicio
        verify(carritoService, times(1))
                .agregarProducto(anyLong(), any(ProductoCompraDTO.class));
    }

    @Test
    @DisplayName("Test DELETE quitar producto del carrito")
    void testQuitarProducto() throws Exception {
        // Configurar mock
        when(carritoService.quitarProducto(anyLong(), anyLong()))
                .thenReturn(carrito);

        // Ejecutar y verificar
        mockMvc.perform(delete("/api/carrito/1/productos/10"))
                .andExpect(status().isOk());

        // Verificar que se llamó al servicio
        verify(carritoService, times(1))
                .quitarProducto(1L, 10L);
    }

    @Test
    @DisplayName("Test GET obtener carrito por ID")
    void testObtenerCarrito() throws Exception {
        // Configurar mock
        when(carritoService.obtenerCarrito(anyLong()))
                .thenReturn(carrito);

        // Ejecutar y verificar
        mockMvc.perform(get("/api/carrito/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1L))
                .andExpect(jsonPath("$.idCliente").value(100L));

        // Verificar que se llamó al servicio
        verify(carritoService, times(1))
                .obtenerCarrito(1L);
    }
}