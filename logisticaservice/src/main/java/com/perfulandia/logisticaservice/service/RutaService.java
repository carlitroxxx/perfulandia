package com.perfulandia.logisticaservice.service;

import com.perfulandia.logisticaservice.model.Ruta;
import com.perfulandia.logisticaservice.repository.RutaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RutaService {
    private final RutaRepository repo;

    public RutaService(RutaRepository repo) {
        this.repo = repo;
    }

    public List<Ruta> listar() {
        return repo.findAll();
    }

    public Ruta guardar(Ruta ruta) {
        return repo.save(ruta);
    }

    public void eliminar(long id) {
        repo.deleteById(id);
    }
}
