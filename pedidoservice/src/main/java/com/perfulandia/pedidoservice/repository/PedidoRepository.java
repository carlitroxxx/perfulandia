package com.perfulandia.pedidoservice.repository;

import com.perfulandia.pedidoservice.model.Pedido;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByIdCliente(long idCliente);
}
