package br.com.postech.mixfast.dataproviders.gateway.mensageria;

import br.com.postech.mixfast.core.gateway.ProducerNotificationGateway;
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

    Region region = Region.US_EAST_1;

    SqsClient sqsClient = SqsClient.builder()
            .region(region)
            .build();

    @Override
    public void notificarPedido() {
        try {
            GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                    .queueName(queueName)
                    .build();

            String queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();

            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody("Olá")
                    .delaySeconds(5)
                    .build();

            sqsClient.sendMessage(sendMsgRequest);
        } catch (SqsException e) {
            System.out.println(e.getMessage());
        }
    }
}
