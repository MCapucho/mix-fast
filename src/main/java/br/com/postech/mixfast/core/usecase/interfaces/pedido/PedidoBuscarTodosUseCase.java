package br.com.postech.mixfast.core.usecase.interfaces.pedido;

import br.com.postech.mixfast.core.entity.Pedido;

import java.util.List;

public interface PedidoBuscarTodosUseCase {

    List<Pedido> buscarTodos();
}
