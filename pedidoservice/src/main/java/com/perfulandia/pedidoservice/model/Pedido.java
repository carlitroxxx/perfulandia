package com.perfulandia.pedidoservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Datos Pedido
    private long id;
    private Date fechaPedido;
    private String estado;
    //Datos Cliente
    private long idCliente;
    private String direccion;

}
