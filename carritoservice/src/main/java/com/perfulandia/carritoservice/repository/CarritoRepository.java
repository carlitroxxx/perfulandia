package com.perfulandia.carritoservice.repository;

import com.perfulandia.carritoservice.model.CarritoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<CarritoModel, Long> {
}
