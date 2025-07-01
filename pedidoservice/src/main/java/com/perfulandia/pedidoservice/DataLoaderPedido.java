package com.perfulandia.pedidoservice;

import com.perfulandia.pedidoservice.model.EstadoPedido;
import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.repository.PedidoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoaderPedido implements CommandLineRunner {

    private final PedidoRepository repo;

    public DataLoaderPedido(PedidoRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        // id (0 o null para que lo genere)
        repo.save(new Pedido(0L, null, EstadoPedido.GENERADO, 1L,"Calle Siempre Viva 742", null));
        repo.save(new Pedido(0L, null, EstadoPedido.ENVIADO, 2L, "Av. Falsa 123", null));
    }
}
