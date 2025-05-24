package com.perfulandia.pedidoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//DTO para obtener desde carrito, el cliente, y la lista de productos y la direccion
public class OrdenCompraDTO {
    private long idCliente;
    private String direccion;
    private List<ProductoCompraDTO> productos;
}
