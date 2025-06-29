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
        //Creamos un carrito
        Carrito carritoMock = new Carrito();
        carritoMock.setIdCarrito(1L);
        carritoMock.setIdCliente(100L);
        carritoMock.setDireccion("Egaña 123");
        //Simulamos
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carritoMock);
        //Ejecutamos y guardamos en otra instancia de carrito
        Carrito resultado = carritoService.crearCarrito(100L, "Calle Falsa 123");
        //Verificamos que los resultados sean los creados
        assertEquals(1L, resultado.getIdCarrito());
        assertEquals(100L, resultado.getIdCliente());
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }
    //?????????????????????????????????
    @Test
    @DisplayName("Test Agregar Producto al Carrito")
    void testAgregarProducto() {
        //Creacion de instancias
        Carrito carritoExistente = new Carrito();
        carritoExistente.setIdCarrito(1L);
        carritoExistente.setProductoCompra(new ArrayList<>());
        //
        ProductoDTO productoMock = new ProductoDTO();
        productoMock.setId(10L);
        productoMock.setNombre("Producto Test");
        productoMock.setPrecio(50);
        productoMock.setStock(100);
        //
        ProductoCompraDTO productoDTO = new ProductoCompraDTO();
        productoDTO.setIdProducto(10L);
        productoDTO.setCantidad(2);
        productoDTO.setPrecio(50);
        productoDTO.setSubtotal(100);
        //Simulamos mock de las 3 instancias
        //Optional es para evitar nulos
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carritoExistente));
        when(restTemplate.getForObject(
                "http://localhost:8082/api/productos/{id}",
                ProductoDTO.class,
                10L
        )).thenReturn(productoMock);
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Ejecutamos y guardamos en una instancia Carrito
        Carrito resultado = carritoService.agregarProducto(1L, productoDTO);
        //Verificamos los datos guardados del carrito
        assertNotNull(resultado);
        assertEquals(1, resultado.getProductoCompra().size());

        ProductoCompra productoAgregado = resultado.getProductoCompra().get(0);
        //Verificamos los datos del producto agregado al carrito
        assertEquals(10L, productoAgregado.getIdProducto());
        assertEquals(2, productoAgregado.getCantidad());
        assertEquals(50, productoAgregado.getPrecio());
        assertEquals(100, productoAgregado.getSubTotal());
        //Verificamoss el uso de carritoRepository y restTemplate
        verify(carritoRepository, times(1)).findById(1L);
        verify(restTemplate).getForObject(
                "http://localhost:8082/api/productos/{id}",
                ProductoDTO.class,
                10L
        );
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }
//?????????????????????????????????????????
    @Test
    @DisplayName("Test Quitar Producto del Carrito")
    void testQuitarProducto() {
        //Creacion de producto
        ProductoCompra producto = new ProductoCompra();
        producto.setIdProducto(10L);
        //creacion del carrito con el producto
        Carrito carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setProductoCompra(new ArrayList<>(List.of(producto)));
        //simulacion
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);
        //Ejecutamos y guardamos en otra instacia carrito
        Carrito resultado = carritoService.quitarProducto(1L, 10L);
        //Verificamos que haya al menos un resultado
        assertNotNull(resultado);
        //Verificamos el uso findById y Save de carrito repository
        verify(carritoRepository, times(1)).findById(1L);
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }

    @Test
    @DisplayName("Test Obtener Carrito")
    void testObtenerCarrito() {
        //instancia de carrito
        Carrito carritoMock = new Carrito();
        carritoMock.setIdCarrito(1L);
        //Simulacion
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carritoMock));
        //Ejecucion y guardamos en otra instacia de carrito para los resultados
        Carrito resultado = carritoService.obtenerCarrito(1L);
        //Verificamos la existencia de datos y que el id sea el mismo
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCarrito());
        //Verificamos el uso de findById de carritoRepository
        verify(carritoRepository, times(1)).findById(1L);
    }
}