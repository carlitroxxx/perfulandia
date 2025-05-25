package com.perfulandia.carritoservice.repository;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.model.ProductosCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<ProductosCompra> findByIdCliente(Long idCliente);
}
