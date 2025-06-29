package com.perfulandia.pedidoservice.controller;

import com.perfulandia.pedidoservice.model.*;
import com.perfulandia.pedidoservice.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Pedido pedido;
    private List<Pedido> pedidos;

    @BeforeEach
    void setUp() {
        List<ProductoCompra> productos = List.of(
                new ProductoCompra(1L, 1L, 2, 100, 200, null)
        );

        pedido = Pedido.builder()
                .id(1L)
                .fechaPedido(LocalDate.now())
                .estado(EstadoPedido.GENERADO)
                .idCliente(100L)
                .direccion("Egaña 123")
                .productos(productos)
                .build();

        pedidos = List.of(pedido);
    }

    @Test
    @DisplayName("Test GET Listar Todos los Pedidos")
    void testListarPedidos() throws Exception {
        // Simulamos servicio
        when(pedidoService.listarPedidos()).thenReturn(pedidos);

        // Ejecutamos y verificamos
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].estado").value("GENERADO"));

        // Verificamos servicio
        verify(pedidoService, times(1)).listarPedidos();
    }

    @Test
    @DisplayName("Test GET Listar Pedidos por ID de Cliente")
    void testListarPedidosPorIdCliente() throws Exception {
        // Simulamos servicio
        when(pedidoService.listarPedidosPorId(100L)).thenReturn(pedidos);

        // Ejecutamos y verificamos
        mockMvc.perform(get("/api/pedidos/cli/{id}", 100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCliente").value(100L));

        // Verificamos servicio
        verify(pedidoService, times(1)).listarPedidosPorId(100L);
    }

    @Test
    @DisplayName("Test GET Buscar Pedido por ID")
    void testBuscarPedido() throws Exception {
        // Simulamos servicio
        when(pedidoService.buscarPedido(1L)).thenReturn(pedido);

        // Ejecutamos y verificamos
        mockMvc.perform(get("/api/pedidos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.estado").value("GENERADO"));

        // Verificamos servicio
        verify(pedidoService, times(1)).buscarPedido(1L);
    }

    @Test
    @DisplayName("Test DELETE Eliminar Pedido")
    void testEliminarPedido() throws Exception {
        // Simulamos servicio
        doNothing().when(pedidoService).eliminarPedido(1L);

        // Ejecutamos y verificamos
        mockMvc.perform(delete("/api/pedidos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Pedido ID: 1 eliminado"));

        // Verificamos servicio
        verify(pedidoService, times(1)).eliminarPedido(1L);
    }

    @Test
    @DisplayName("Test POST Generar Pedido desde Carrito")
    void testGenerarPedido() throws Exception {
        // Simulamos servicio
        when(pedidoService.recibirOrden(1L)).thenReturn(pedido);

        // Ejecutamos y verificamos
        mockMvc.perform(post("/api/pedidos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.estado").value("GENERADO"));

        // Verificamos servicio
        verify(pedidoService, times(1)).recibirOrden(1L);
    }

    @Test
    @DisplayName("Test PUT Cambiar Estado de Pedido")
    void testCambiarEstadoPedido() throws Exception {
        // Configuramos pedido mock
        pedido.setEstado(EstadoPedido.ENVIADO);

        // Simulamos servicio
        when(pedidoService.cambiarEstadoPedido(1L, EstadoPedido.ENVIADO)).thenReturn(pedido);

        // Ejecutamos y verificamos
        mockMvc.perform(put("/api/pedidos/{id}/estado?estado=ENVIADO", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ENVIADO"));

        // Verificamos servicio
        verify(pedidoService, times(1))
                .cambiarEstadoPedido(1L, EstadoPedido.ENVIADO);
    }
}