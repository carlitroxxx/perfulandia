package com.perfulandia.usuarioservice.service;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Librerias Mockito
import org.mockito.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {
    //Instancia de usuarioservice para que mockito pueda simular datos
    @InjectMocks
    private UsuarioService usuarioService;

    //Mock de repository
    @Mock
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceTest(){
        //Inicializar mocks
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Test Listar Usuario")
    void testFindAll(){
        //Simular mock findAll y nos devuelve el usuario con sus datos
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario(1L, "Carlos", "carlosmoil@gmail.com", "CLIENTE")));
        //Ejecutamos y guardamos los resultados
        List<Usuario> resultado = usuarioService.listar();
        //Verificar que hay solo un resultado
        assertEquals(1, resultado.size());
        //Verificamos que se utilizó findAll() de usuarioRepository
        verify(usuarioRepository, times(1)).findAll();
    }
    @Test
    @DisplayName("Test Guardar Usuario")
    void testSave() {
        // Creamos objeto usuario
        Usuario usuario = new Usuario(1L, "Mayckol", "mayckolmardones@gmail.com", "CLIENTE");
        //Simulamos mock
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        // Ejecutamos y guardamos
        Usuario resultado = usuarioService.guardar(usuario);
        // Verificamos
        assertEquals("Mayckol", resultado.getNombre());
        //Verificamos que se utilizó save() de usuarioRepository
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
    @Test
    @DisplayName("Test Buscar Usuario por ID")
    void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(new Usuario(1L,"Francisco","fra.vera@gmail.com","CLIENTE")));
        Usuario resultado = usuarioService.buscar(1L);
        assertNotNull(resultado);
        assertEquals("Francisco", resultado.getNombre());
        verify(usuarioRepository, times(1)).findById(1L);
    }
}
