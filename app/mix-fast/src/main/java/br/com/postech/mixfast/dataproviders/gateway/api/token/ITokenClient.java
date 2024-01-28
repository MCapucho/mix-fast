package br.com.postech.mixfast.dataproviders.gateway.api.token;

import br.com.postech.mixfast.dataproviders.model.rest.token.TokenApiRequest;
import br.com.postech.mixfast.dataproviders.model.rest.token.TokenApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "token", url = "${feign.client.config.token.url}", configuration = TokenConfig.class)
public interface ITokenClient {

    @PostMapping()
    TokenApiResponse gerarToken(@RequestBody TokenApiRequest tokenApiRequest);
}
