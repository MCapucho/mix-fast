package br.com.postech.mixfast.core.gateway;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.entity.Pedido;

public interface ProducerNotificationGateway {

    void notificarPedido(Pedido pedido, Cliente cliente, String queueName);
}
