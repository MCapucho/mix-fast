package br.com.postech.mixfast.dataproviders.gateway.api.formapagamento;

import br.com.postech.mixfast.dataproviders.model.rest.formapagamento.FormaPagamentoApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "formapagamento", url = "${feign.client.config.formapagamento.url}", configuration = FormaPagamentoConfig.class)
public interface IFormaPagamentoClient {

    @GetMapping("/{codigo}")
    FormaPagamentoApiResponse buscarPorCodigo(@PathVariable("codigo") String codigo);
}
