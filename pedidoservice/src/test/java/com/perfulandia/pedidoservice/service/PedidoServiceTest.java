package com.perfulandia.pedidoservice.service;

import com.perfulandia.pedidoservice.model.*;
import com.perfulandia.pedidoservice.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private OrdenCompraDTO ordenCompraDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<ProductoCompraDTO> productosDTO = List.of(
                new ProductoCompraDTO(1L, 2, 100, 200)
        );

        ordenCompraDTO = new OrdenCompraDTO(100L, "Egaña 123", productosDTO);

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
    }

    @Test
    @DisplayName("Test Listar Todos los Pedidos")
    void testListarPedidos() {
        // Simulamos repositorio
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

        // Ejecutamos servicio
        List<Pedido> resultado = pedidoService.listarPedidos();

        // Verificamos resultados
        assertEquals(1, resultado.size());
        assertEquals(100L, resultado.get(0).getIdCliente());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Listar Pedidos por ID de Cliente")
    void testListarPedidosPorId() {
        // Simulamos repositorio
        when(pedidoRepository.findByIdCliente(100L)).thenReturn(List.of(pedido));

        // Ejecutamos servicio
        List<Pedido> resultado = pedidoService.listarPedidosPorId(100L);

        // Verificamos resultados
        assertEquals(1, resultado.size());
        assertEquals(100L, resultado.get(0).getIdCliente());
        verify(pedidoRepository, times(1)).findByIdCliente(100L);
    }

    @Test
    @DisplayName("Test Buscar Pedido por ID")
    void testBuscarPedido() {
        // Simulamos repositorio
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        // Ejecutamos servicio
        Pedido resultado = pedidoService.buscarPedido(1L);

        // Verificamos resultados
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test Eliminar Pedido")
    void testEliminarPedido() {
        // Simulamos repositorio
        doNothing().when(pedidoRepository).deleteById(1L);

        // Ejecutamos servicio
        pedidoService.eliminarPedido(1L);

        // Verificamos repositorio
        verify(pedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Test Recibir Orden y Crear Pedido")
    void testRecibirOrden() {
        // Simulamos servicio externo y repositorio
        when(restTemplate.getForObject("http://localhost:8084/api/carrito/1", OrdenCompraDTO.class))
                .thenReturn(ordenCompraDTO);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        // Ejecutamos servicio
        Pedido resultado = pedidoService.recibirOrden(1L);

        // Verificamos resultados
        assertNotNull(resultado);
        assertEquals(EstadoPedido.GENERADO, resultado.getEstado());
        assertEquals(100L, resultado.getIdCliente());
        verify(restTemplate, times(1))
                .getForObject("http://localhost:8084/api/carrito/1", OrdenCompraDTO.class);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Test Cambiar Estado de Pedido")
    void testCambiarEstadoPedido() {
        // Simulamos repositorio
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        // Ejecutamos servicio
        Pedido resultado = pedidoService.cambiarEstadoPedido(1L, EstadoPedido.ENVIADO);

        // Verificamos resultados
        assertEquals(EstadoPedido.ENVIADO, resultado.getEstado());
        verify(pedidoRepository, times(1)).findById(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Test Cambiar Estado de Pedido - Pedido No Encontrado")
    void testCambiarEstadoPedidoNoEncontrado() {
        // Simulamos repositorio
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificamos excepción
        assertThrows(RuntimeException.class, () -> {
            pedidoService.cambiarEstadoPedido(1L, EstadoPedido.ENVIADO);
        });

        // Verificamos repositorio
        verify(pedidoRepository, times(1)).findById(1L);
    }
}