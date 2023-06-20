package br.com.postech.mixfast.core.gateway;

import br.com.postech.mixfast.core.entity.Pedido;

import java.util.List;

public interface PedidoGateway {

    Pedido enviar(Pedido pedido);
    List<Pedido> buscarTodos();
    Pedido buscarPorCodigo(String codigo);
    void atualizar(Pedido pedido);
}
