package com.perfulandia.usuarioservice.service;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    public UsuarioServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    private Usuario crearUsuarioEjemplo() {
        return new Usuario(1L, "Carlos", "carlosmoil@gmail.com", "CLIENTE");
    }

    @Test
    @DisplayName("Test Listar Usuarios")
    void testListarUsuarios() {
        Usuario usuario = crearUsuarioEjemplo();
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Guardar Usuario")
    void testGuardarUsuario() {
        Usuario usuario = crearUsuarioEjemplo();
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.guardar(usuario);

        assertEquals("Carlos", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Test Buscar Usuario por ID")
    void testBuscarUsuarioExistente() {
        Usuario usuario = crearUsuarioEjemplo();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscar(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(usuarioRepository, times(1)).findById(1L);
    }



    @Test
    @DisplayName("Test Eliminar Usuario")
    void testEliminarUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.eliminar(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}