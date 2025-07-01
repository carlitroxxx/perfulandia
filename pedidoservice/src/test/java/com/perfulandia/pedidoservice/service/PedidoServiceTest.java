package com.perfulandia.pedidoservice.service;

import com.perfulandia.pedidoservice.model.*;
import com.perfulandia.pedidoservice.repository.PedidoRepository;
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

    public PedidoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    private Pedido crearPedidoEjemplo() {
        List<ProductoCompra> productos = List.of(
                new ProductoCompra(1L, 1L, 2, 100, 200, null)
        );
        return Pedido.builder()
                .id(1L)
                .fechaPedido(LocalDate.now())
                .estado(EstadoPedido.GENERADO)
                .idCliente(100L)
                .direccion("Egaña 123")
                .productos(productos)
                .build();
    }

    private OrdenCompraDTO crearOrdenEjemplo() {
        List<ProductoCompraDTO> productosDTO = List.of(
                new ProductoCompraDTO(1L, 2, 100, 200)
        );
        return new OrdenCompraDTO(100L, "Egaña 123", productosDTO);
    }

    @Test
    @DisplayName("Test Listar Todos los Pedidos")
    void testListarPedidos() {
        Pedido pedido = crearPedidoEjemplo();
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

        List<Pedido> resultado = pedidoService.listarPedidos();

        assertEquals(1, resultado.size());
        assertEquals(100L, resultado.get(0).getIdCliente());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Listar Pedidos por ID de Cliente")
    void testListarPedidosPorId() {
        Pedido pedido = crearPedidoEjemplo();
        when(pedidoRepository.findByIdCliente(100L)).thenReturn(List.of(pedido));

        List<Pedido> resultado = pedidoService.listarPedidosPorId(100L);

        assertEquals(1, resultado.size());
        assertEquals(100L, resultado.get(0).getIdCliente());
        verify(pedidoRepository, times(1)).findByIdCliente(100L);
    }

    @Test
    @DisplayName("Test Buscar Pedido por ID")
    void testBuscarPedido() {
        Pedido pedido = crearPedidoEjemplo();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido resultado = pedidoService.buscarPedido(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test Eliminar Pedido")
    void testEliminarPedido() {
        doNothing().when(pedidoRepository).deleteById(1L);

        pedidoService.eliminarPedido(1L);

        verify(pedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Test Recibir Orden y Crear Pedido")
    void testRecibirOrden() {
        OrdenCompraDTO orden = crearOrdenEjemplo();
        Pedido pedido = crearPedidoEjemplo();

        when(restTemplate.getForObject("http://localhost:8084/api/carrito/1", OrdenCompraDTO.class))
                .thenReturn(orden);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.recibirOrden(1L);

        assertNotNull(resultado);
        assertEquals(EstadoPedido.GENERADO, resultado.getEstado());
        verify(restTemplate, times(1))
                .getForObject("http://localhost:8084/api/carrito/1", OrdenCompraDTO.class);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Test Cambiar Estado de Pedido")
    void testCambiarEstadoPedido() {
        Pedido pedido = crearPedidoEjemplo();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.cambiarEstadoPedido(1L, EstadoPedido.ENVIADO);

        assertEquals(EstadoPedido.ENVIADO, resultado.getEstado());
        verify(pedidoRepository, times(1)).findById(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

}