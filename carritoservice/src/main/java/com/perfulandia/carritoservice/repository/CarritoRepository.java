package com.perfulandia.carritoservice.repository;

import com.perfulandia.carritoservice.model.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<CarritoItem, Long> {
}
