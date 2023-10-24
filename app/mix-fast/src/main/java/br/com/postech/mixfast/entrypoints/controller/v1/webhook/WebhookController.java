package br.com.postech.mixfast.entrypoints.controller.v1.webhook;

import br.com.postech.mixfast.core.usecase.interfaces.webhook.WebhookUseCase;
import br.com.postech.mixfast.entrypoints.docs.WebhookDocumentable;
import br.com.postech.mixfast.entrypoints.http.WebhookHttp;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/v1/webhooks")
public class WebhookController implements WebhookDocumentable {

    private final WebhookUseCase webhookUseCase;

    @PostMapping
    public ResponseEntity<?> receberNotificacaoPagamento(@RequestBody WebhookHttp webhookHttp) {
        webhookUseCase.atualizar(webhookHttp.getCodigoPedido(), webhookHttp.getStatusPagamento());
        log.info("Status do pagamento atualizado no pedido com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(
                String.format("Status do pagamento atualizado para o pedido %s", webhookHttp.getCodigoPedido()));
    }
}
