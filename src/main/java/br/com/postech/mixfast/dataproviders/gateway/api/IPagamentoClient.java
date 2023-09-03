package br.com.postech.mixfast.dataproviders.gateway.api;

import br.com.postech.mixfast.dataproviders.model.rest.DadosPagamentoRequest;
import br.com.postech.mixfast.dataproviders.model.rest.DadosPagamentoResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "pagamento", url = "${feign.client.config.url}", configuration = PagamentoConfig.class)
public interface IPagamentoClient {

    @PostMapping
    DadosPagamentoResponse gerarQRCode(@Valid @RequestBody DadosPagamentoRequest dadosPagamentoRequest,
                                       @RequestHeader("Authorization") String token);
}
