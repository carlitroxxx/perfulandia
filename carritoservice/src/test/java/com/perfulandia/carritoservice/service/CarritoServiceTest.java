package com.perfulandia.carritoservice.service;

import com.perfulandia.carritoservice.model.*;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CarritoService carritoService;

    public CarritoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Crear Carrito")
    void testCrearCarrito() {
        Carrito carritoMock = new Carrito();
        carritoMock.setIdCarrito(1L);
        carritoMock.setIdCliente(100L);
        carritoMock.setDireccion("Egaña 123");

        when(carritoRepository.save(any(Carrito.class))).thenReturn(carritoMock);

        Carrito resultado = carritoService.crearCarrito(100L, "Calle Falsa 123");

        assertEquals(1L, resultado.getIdCarrito());
        assertEquals(100L, resultado.getIdCliente());
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }

    @Test
    @DisplayName("Test Agregar Producto al Carrito")
    void testAgregarProducto() {
        // Configuración
        Carrito carritoExistente = new Carrito();
        carritoExistente.setIdCarrito(1L);
        carritoExistente.setProductoCompra(new ArrayList<>());

        ProductoDTO productoMock = new ProductoDTO();
        productoMock.setId(10L);
        productoMock.setPrecio(50);

        ProductoCompraDTO productoDTO = new ProductoCompraDTO();
        productoDTO.setIdProducto(10L);
        productoDTO.setCantidad(2);

        // Mock
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carritoExistente));
        when(restTemplate.getForObject(
                "http://localhost:8082/api/productos/{id}",
                ProductoDTO.class,
                10L
        )).thenReturn(productoMock);
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecución
        Carrito resultado = carritoService.agregarProducto(1L, productoDTO);

        // Verificación
        assertNotNull(resultado);
        assertEquals(1, resultado.getProductoCompra().size());

        ProductoCompra productoAgregado = resultado.getProductoCompra().get(0);
        assertEquals(10L, productoAgregado.getIdProducto());
        assertEquals(2, productoAgregado.getCantidad());
        assertEquals(50, productoAgregado.getPrecio());
        assertEquals(100, productoAgregado.getSubTotal());

        verify(carritoRepository, times(1)).findById(1L);
        verify(restTemplate).getForObject(
                "http://localhost:8082/api/productos/{id}",
                ProductoDTO.class,
                10L
        );
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }


    @Test
    @DisplayName("Test Quitar Producto del Carrito")
    void testQuitarProducto() {
        ProductoCompra producto = new ProductoCompra();
        producto.setIdProducto(10L);

        Carrito carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setProductoCompra(new ArrayList<>(List.of(producto)));

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        Carrito resultado = carritoService.quitarProducto(1L, 10L);

        assertNotNull(resultado);
        assertTrue(resultado.getProductoCompra().isEmpty());
        verify(carritoRepository, times(1)).findById(1L);
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }

    @Test
    @DisplayName("Test Obtener Carrito")
    void testObtenerCarrito() {
        Carrito carritoMock = new Carrito();
        carritoMock.setIdCarrito(1L);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carritoMock));

        Carrito resultado = carritoService.obtenerCarrito(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCarrito());
        verify(carritoRepository, times(1)).findById(1L);
    }


}