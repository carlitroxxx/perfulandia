package com.perfulandia.logisticaservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String direccionEntrega;
    private double distanciaKm;

    private long repartidorId; // Relación con Usuario de otro microservicio
}
