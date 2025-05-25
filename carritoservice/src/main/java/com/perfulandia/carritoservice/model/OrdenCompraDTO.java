package com.perfulandia.carritoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompraDTO {
    private long idCliente;
    private String direccion;
    private List<ProductoCompraDTO> productos = new ArrayList<>();
}
