package com.perfulandia.logisticaservice.controller;

import com.perfulandia.logisticaservice.model.Ruta;
import com.perfulandia.logisticaservice.model.Repartidor;
import com.perfulandia.logisticaservice.service.RutaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/logistica")
public class LogisticaController {

    private final RutaService service;
    private final RestTemplate restTemplate;

    public LogisticaController(RutaService service, RestTemplate restTemplate) {
        this.service = service;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/rutas")
    public List<Ruta> listar() {
        return service.listar();
    }

    @PostMapping("/rutas")
    public Ruta guardar(@RequestBody Ruta ruta) {
        return service.guardar(ruta);
    }

    @DeleteMapping("/rutas/{id}")
    public void eliminar(@PathVariable long id) {
        service.eliminar(id);
    }

    @GetMapping("/repartidor/{id}")
    public Repartidor obtenerRepartidor(@PathVariable long id) {
        return restTemplate.getForObject("http://localhost:8081/api/usuarios/" + id, Repartidor.class);
    }
}
