package com.perfulandia.carritoservice.repository;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.ProductoCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<Carrito> findByIdCliente(Long idCliente);
}