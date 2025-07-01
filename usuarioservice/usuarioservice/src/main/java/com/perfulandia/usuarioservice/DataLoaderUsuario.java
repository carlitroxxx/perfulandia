package com.perfulandia.usuarioservice;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoaderUsuario implements CommandLineRunner {

    private final UsuarioRepository repo;

    public DataLoaderUsuario(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        repo.save(new Usuario(0L, "Ana Pérez", "ana@perfumeria.com", "ADMIN"));
        repo.save(new Usuario(0L, "Carlos Ríos", "carlos@perfumeria.com", "GERENTE"));
        repo.save(new Usuario(0L, "Laura Gómez", "laura@perfumeria.com", "USUARIO"));
    }
}
