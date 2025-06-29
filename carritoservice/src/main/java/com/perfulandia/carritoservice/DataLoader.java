package com.perfulandia.carritoservice;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.repository.CarritoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CarritoRepository repo;

    public DataLoader(CarritoRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        repo.save(new Carrito(0L, 1L, "Av. Siempre Viva 742", null));
        repo.save(new Carrito(0L, 2L, "Calle Falsa 123", null));
    }
}