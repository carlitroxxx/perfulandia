package com.perfulandia.pedidoservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    private LocalDate fechaPedido;
    private String estado;
    //Cliente asociado
    private long idCliente;
    private String direccion;

    // Orden de Compra
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    List<ProductoCompra> productos;

}
