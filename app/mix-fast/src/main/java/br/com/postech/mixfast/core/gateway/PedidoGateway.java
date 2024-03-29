package br.com.postech.mixfast.core.gateway;

import br.com.postech.mixfast.core.entity.Pedido;

import java.util.List;

public interface PedidoGateway {

    Pedido emitir(Pedido pedido);
    List<Pedido> buscarTodos();
    Pedido buscarPorCodigo(String codigo);
    void atualizarStatusPedido(Pedido pedido);
    void atualizarStatusPagamento(Pedido pedido);
    List<Pedido> buscarPorStatusPedido(String status);
}
