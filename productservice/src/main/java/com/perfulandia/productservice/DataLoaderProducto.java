package com.perfulandia.productservice;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.repository.ProductoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoaderProducto implements CommandLineRunner {

    private final ProductoRepository repo;

    public DataLoaderProducto(ProductoRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        repo.save(new Producto(0L, "Perfume Rosa", 14990, 10));
        repo.save(new Producto(0L, "Colonia Cítrica", 9990, 15));
        repo.save(new Producto(0L, "Aroma Vainilla", 12990, 8));
        repo.save(new Producto(0L, "Esencia Lavanda", 8990, 12));
    }
}
