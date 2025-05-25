package com.perfulandia.logisticaservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Repartidor {
    private long id;
    private String nombre;
    private String correo;
    private String rol;

}
