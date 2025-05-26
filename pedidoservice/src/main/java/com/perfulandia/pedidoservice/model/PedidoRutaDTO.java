package com.perfulandia.pedidoservice.model;

import lombok.Data;

@Data
public class PedidoRutaDTO {
    private String direccionEntrega;
    private long idCliente;
    private long repartidorId;
}
