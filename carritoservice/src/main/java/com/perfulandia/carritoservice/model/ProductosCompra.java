package com.perfulandia.carritoservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class ProductosCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long idProducto;
    private int cantidad;
    private double precio;
    private double subTotal;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;
}
