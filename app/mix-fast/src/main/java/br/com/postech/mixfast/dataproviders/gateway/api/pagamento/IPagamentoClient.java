package br.com.postech.mixfast.dataproviders.gateway.api.pagamento;

import br.com.postech.mixfast.dataproviders.model.rest.pagamento.PagamentoApiRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pagamento", url = "${feign.client.config.pagamento.url}", configuration = PagamentoConfig.class)
public interface IPagamentoClient {

    @PostMapping
    String gerarQRCode(@Valid @RequestBody PagamentoApiRequest pagamentoAPIRequest);
}
