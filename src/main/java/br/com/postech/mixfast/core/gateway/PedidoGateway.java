package br.com.postech.mixfast.core.gateway;

import br.com.postech.mixfast.core.entity.Pedido;

import java.util.List;

public interface PedidoGateway {

    Pedido cadastrar(Pedido pedido);
    List<Pedido> buscarTodos();
}
