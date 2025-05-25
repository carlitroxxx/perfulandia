package com.perfulandia.carritoservice.service;

import com.perfulandia.carritoservice.model.CarritoItem;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {
    private final CarritoRepository carritoRepository;

    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    public List<CarritoItem> getCarrito() {
        return carritoRepository.findAll();
    }

    public CarritoItem addProducto(CarritoItem carritoItem) {
        return carritoRepository.save(carritoItem);
    }

    public void eliminarProducto(Long id) {
        carritoRepository.deleteById(id);
    }
}