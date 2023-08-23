package br.com.postech.mixfast.entrypoints.controller.v1.webhook;

import br.com.postech.mixfast.entrypoints.http.WebhookHttp;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/v1/webhooks")
public class WebhookController {

    @PostMapping
    public ResponseEntity<?> receberNotificacaoPagamento(@RequestBody WebhookHttp webhookHttp) {
        System.out.println(webhookHttp);
        return ResponseEntity.ok().build();
    }
}
