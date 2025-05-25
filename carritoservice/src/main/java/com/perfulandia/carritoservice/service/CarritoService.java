package com.perfulandia.carritoservice.service;

import com.perfulandia.carritoservice.model.CarritoModel;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {
    private final CarritoRepository carritoRepository;

    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    public List<CarritoModel> getCarrito() {
        return carritoRepository.findAll();
    }

    public CarritoModel addProducto(CarritoModel carritoItem) {
        return carritoRepository.save(carritoItem);
    }

    public void eliminarProducto(Long id) {
        carritoRepository.deleteById(id);
    }
}