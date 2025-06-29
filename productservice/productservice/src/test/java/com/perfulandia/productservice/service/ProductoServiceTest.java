package com.perfulandia.productservice.service;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.repository.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//
import org.mockito.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {
    //mock service
    @InjectMocks
    private ProductoService productoService;

    //mock repo
    @Mock
    private ProductoRepository productoRepository;

    public ProductoServiceTest() {
        //init mock
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Test Listar Producto")
    void testFindAll() {
        //Simular mock findAll y nos devuelve el producto con sus datos
        when(productoRepository.findAll()).thenReturn(List.of(new Producto(1L,"Sauvage Eau de Parfum 100ml",173990,10)));
        //Ejecutamos y lo guardamos en una lista
        List<Producto> resultado = productoService.listar();
        //Verificar que hay solo un resultado en la lista
        assertEquals(1, resultado.size());
        //Verificamos que el nombre del perfume sea el de la consulta
        assertEquals("Sauvage Eau de Parfum 100ml", resultado.get(0).getNombre());
        //Verificamos que se utilizó findAll() de productoRepository
        verify(productoRepository, times(1)).findAll();
    }
    @Test
    @DisplayName("Test Guardar Producto")
    void testSave() {
        //Objeto perfume
        Producto producto = new Producto(1L,"Sauvage Eau de Parfum 100ml",173990,10);
        //Simulamos el producto
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        //Ejecutamos y lo guardamos en otra instancia
        Producto resultado = productoService.guardar(producto);
        //Verificamos que el producto guardado tenga el mismo nombre
        assertEquals("Sauvage Eau de Parfum 100ml", resultado.getNombre());
        //Segunda Verificacion con el precio
        assertEquals(173990, resultado.getPrecio());
        //Verificamos que se haya utilizado save() de productoRepository
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Test Buscar Producto por ID")
    void testFindById() {
        //Simulamos un producto
        when(productoRepository.findById(1L)).thenReturn(java.util.Optional.of(new Producto(1L,"Sauvage Eau de Parfum 100ml",173990,10)));
        //Ejecutamos y guardamos el producto en una instancia
        Producto resultado = productoService.bucarPorId(1L);
        //Verificamos que si haya algun resultado
        assertNotNull(resultado);
        //Verificamos que el nombre sea el mismo
        assertEquals("Sauvage Eau de Parfum 100ml", resultado.getNombre());
        //Verificamos que se haya utilizado findById() de productoRepository
        verify(productoRepository, times(1)).findById(1L);
    }
}