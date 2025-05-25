package com.perfulandia.carritoservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCarrito;
    private long idCliente;
    private String direccion;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<ProductosCompra> productosCompra;
}