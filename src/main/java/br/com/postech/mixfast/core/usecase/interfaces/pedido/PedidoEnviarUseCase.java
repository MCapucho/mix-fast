package br.com.postech.mixfast.core.usecase.interfaces.pedido;

import br.com.postech.mixfast.core.entity.Pedido;

public interface PedidoEnviarUseCase {

    Pedido enviar(Pedido pedido);
}
