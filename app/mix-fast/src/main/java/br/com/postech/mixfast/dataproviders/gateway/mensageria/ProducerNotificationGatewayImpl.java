package br.com.postech.mixfast.dataproviders.gateway.mensageria;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.gateway.ProducerNotificationGateway;
import br.com.postech.mixfast.dataproviders.model.mensageria.NotificacaoRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

@RequiredArgsConstructor
@Service
public class ProducerNotificationGatewayImpl implements ProducerNotificationGateway {

    @Value("${aws.queue.name}")
    private String queueName;

    private final Gson gson;

    Region region = Region.US_EAST_1;

    SqsClient sqsClient = SqsClient.builder()
            .region(region)
            .build();

    @Override
    public void notificarPedido(Pedido pedido, Cliente cliente) {
        if (pedido.getCliente() != null) {
            NotificacaoRequest notificacaoRequest = NotificacaoRequest.builder()
                    .codigoPedido(pedido.getCodigo())
                    .nomeCliente(cliente.getNome())
                    .emailCliente(cliente.getEmail())
                    .valorTotalPedido(pedido.getValorTotal())
                    .statusPedido(pedido.getStatusPedido().getDescricao())
                    .statusPagamento(pedido.getStatusPagamento().getDescricao())
                    .build();

            String notificacaoJson = gson.toJson(notificacaoRequest);

            try {
                GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                        .queueName(queueName)
                        .build();

                String queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();

                SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .messageBody(notificacaoJson)
                        .delaySeconds(5)
                        .build();

                sqsClient.sendMessage(sendMsgRequest);
            } catch (SqsException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
