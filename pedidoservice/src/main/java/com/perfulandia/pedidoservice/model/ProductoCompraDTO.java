package com.perfulandia.pedidoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCompraDTO {
    private long idProducto;
    private int cantidad;
    private double precio;
    private double subtotal;
}
