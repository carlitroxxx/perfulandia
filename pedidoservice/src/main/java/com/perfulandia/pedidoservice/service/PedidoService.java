package com.perfulandia.pedidoservice.service;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    public final PedidoRepository repo;
    public PedidoService(PedidoRepository repo) {this.repo = repo;}

    //Lista con todos los pedidos
    public List<Pedido> listarPedidos() {return repo.findAll();}
    //Lista con pedidos segun cliente
    public List<Pedido> listarPedidosPorId(long idCliente){return repo.findByIdCliente(idCliente);}
    //Generar pedido
    public Pedido generarPedido(Pedido pedido){return repo.save(pedido);}
    //Buscar pedido
    public Pedido buscarPedido(long id){return repo.findById(id).orElse(null);}
    //Eliminar pedido
    public void eliminarPedido(long id){repo.deleteById(id);}
}
