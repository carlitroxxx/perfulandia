package com.perfulandia.pedidoservice.model;

import lombok.Data;

@Data
public class ProductoCompraDTO {
    private long idProducto;
    private int cantidad;
    private double precio;
    private double subtotal;
}
