package com.perfulandia.productservice.service;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.repository.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    public ProductoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    private Producto crearProductoEjemplo() {
        return new Producto(1L, "Sauvage Eau de Parfum 100ml", 173990, 10);
    }

    @Test
    @DisplayName("Test Listar Productos")
    void testListarProductos() {
        Producto producto = crearProductoEjemplo();
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> resultado = productoService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Sauvage Eau de Parfum 100ml", resultado.get(0).getNombre());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Guardar Producto")
    void testGuardarProducto() {
        Producto producto = crearProductoEjemplo();
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.guardar(producto);

        assertEquals("Sauvage Eau de Parfum 100ml", resultado.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Test Buscar Producto por ID")
    void testBuscarProductoExistente() {
        Producto producto = crearProductoEjemplo();
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.bucarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(productoRepository, times(1)).findById(1L);
    }


    @Test
    @DisplayName("Test Eliminar Producto")
    void testEliminarProducto() {
        doNothing().when(productoRepository).deleteById(1L);

        productoService.eliminar(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }
}