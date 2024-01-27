package br.com.postech.mixfast.dataproviders.gateway.api.formapagamento;

import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import br.com.postech.mixfast.dataproviders.model.rest.formapagamento.FormaPagamentoApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FormaPagamentoGatewayImpl implements FormaPagamentoGateway {

    private final IFormaPagamentoClient formaPagamentoClient;

    @Override
    public String buscarPorCodigo(String codigo) {
        FormaPagamentoApiResponse formaPagamentoApiResponse = formaPagamentoClient.buscarPorCodigo(codigo);

        return formaPagamentoApiResponse.getDescricao();
    }
}
