package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.assembler.CarritoAssembler;
import com.perfulandia.carritoservice.model.*;
import com.perfulandia.carritoservice.service.CarritoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
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

    @MockitoBean
    private CarritoAssembler carritoAssembler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Test POST crear carrito")
    void testCrearCarrito() throws Exception {
        // Datos de prueba
        Carrito carritoRequest = new Carrito();
        carritoRequest.setIdCliente(100L);
        carritoRequest.setDireccion("Egaña 123");

        Carrito carritoResponse = new Carrito(1L, 100L, "Egaña 123", new ArrayList<>());
        EntityModel<Carrito> entityModel = EntityModel.of(carritoResponse);

        // Configurar mocks
        when(carritoService.crearCarrito(100L, "Egaña 123")).thenReturn(carritoResponse);
        when(carritoAssembler.toModel(carritoResponse)).thenReturn(entityModel);

        // Ejecutar y verificar
        mockMvc.perform(post("/api/carrito")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(carritoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1L))
                .andExpect(jsonPath("$.idCliente").value(100L));

        verify(carritoService, times(1)).crearCarrito(100L, "Egaña 123");
        verify(carritoAssembler, times(1)).toModel(carritoResponse);
    }

    @Test
    @DisplayName("Test POST agregar producto al carrito")
    void testAgregarProducto() throws Exception {
        // Datos de prueba
        ProductoCompraDTO productoDTO = new ProductoCompraDTO(10L, 2, 50, 100);
        Carrito carritoConProducto = new Carrito(1L, 100L, "Egaña 123",
                List.of(new ProductoCompra(1L, 10L, 2, 50, 100, null)));
        EntityModel<Carrito> entityModel = EntityModel.of(carritoConProducto);

        // Configurar mocks
        when(carritoService.agregarProducto(1L, productoDTO)).thenReturn(carritoConProducto);
        when(carritoAssembler.toModel(carritoConProducto)).thenReturn(entityModel);

        // Ejecutar y verificar
        mockMvc.perform(post("/api/carrito/1/productos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoCompra[0].idProducto").value(10L))
                .andExpect(jsonPath("$.productoCompra[0].subTotal").value(100));

        verify(carritoService, times(1)).agregarProducto(1L, productoDTO);
        verify(carritoAssembler, times(1)).toModel(carritoConProducto);
    }

    @Test
    @DisplayName("Test DELETE quitar producto del carrito")
    void testQuitarProducto() throws Exception {
        // Datos de prueba
        Carrito carrito = new Carrito(1L, 100L, "Egaña 123", new ArrayList<>());
        EntityModel<Carrito> entityModel = EntityModel.of(carrito);

        // Configurar mocks
        when(carritoService.quitarProducto(1L, 10L)).thenReturn(carrito);
        when(carritoAssembler.toModel(carrito)).thenReturn(entityModel);

        // Ejecutar y verificar
        mockMvc.perform(delete("/api/carrito/1/productos/10"))
                .andExpect(status().isOk());

        verify(carritoService, times(1)).quitarProducto(1L, 10L);
        verify(carritoAssembler, times(1)).toModel(carrito);
    }

    @Test
    @DisplayName("Test GET obtener carrito por ID")
    void testObtenerCarrito() throws Exception {
        // Datos de prueba
        Carrito carrito = new Carrito(1L, 100L, "Egaña 123", new ArrayList<>());
        EntityModel<Carrito> entityModel = EntityModel.of(carrito);

        // Configurar mocks
        when(carritoService.obtenerCarrito(1L)).thenReturn(carrito);
        when(carritoAssembler.toModel(carrito)).thenReturn(entityModel);

        // Ejecutar y verificar
        mockMvc.perform(get("/api/carrito/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1L))
                .andExpect(jsonPath("$.idCliente").value(100L));

        verify(carritoService, times(1)).obtenerCarrito(1L);
        verify(carritoAssembler, times(1)).toModel(carrito);
    }
}