package com.perfulandia.carritoservice.model;

import lombok.Data;

@Data
public class ProductoDTO {
    private long id;
    private String nombre;
    private double precio;
    private int stock;
}
