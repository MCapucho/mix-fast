package br.com.postech.mixfast.core.usecase.impl.webhook;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.entity.enums.StatusPagamento;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.gateway.ProducerNotificationGateway;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.webhook.WebhookUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class WebhookUseCaseImpl implements WebhookUseCase {

    @Value("${aws.queue.name.aprovado}")
    private String queueAprovado;

    @Value("${aws.queue.name.reprovado}")
    private String queueReprovado;

    @Value("${aws.queue.name.cozinha}")
    private String queueCozinha;

    private final PedidoBuscarPorCodigoUseCase pedidoBuscarPorCodigoUseCase;
    private final PedidoGateway pedidoGateway;
    private final ProducerNotificationGateway producerNotificationGateway;

    @Override
    public void atualizar(String codigoPedido, String statusPagamento) {
        Pedido pedido = pedidoBuscarPorCodigoUseCase.buscarPorCodigo(codigoPedido);
        pedido.setStatusPagamento(Objects.equals(statusPagamento.toUpperCase(), StatusPagamento.APROVADO.name()) ?
                StatusPagamento.APROVADO : StatusPagamento.REPROVADO);
        pedidoGateway.atualizarStatusPagamento(pedido);

        if (pedido.getStatusPagamento() == StatusPagamento.APROVADO) {
            producerNotificationGateway.notificarPedido(pedido, pedido.getCliente(), queueAprovado);
            producerNotificationGateway.notificarPedido(pedido, pedido.getCliente(), queueCozinha);
        } else {
            producerNotificationGateway.notificarPedido(pedido, pedido.getCliente(), queueReprovado);
        }
    }
}
