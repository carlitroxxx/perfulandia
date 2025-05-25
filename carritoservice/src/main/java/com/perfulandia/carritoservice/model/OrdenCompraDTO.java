package com.perfulandia.carritoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompraDTO {
    private long idCliente;
    private String direccion;
    private List<ProductoCompraDTO> productos = new ArrayList<>();
}
