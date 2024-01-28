package br.com.postech.mixfast.dataproviders.gateway.api.pagamento;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.gateway.PagamentoGateway;
import br.com.postech.mixfast.dataproviders.gateway.api.token.TokenGatewayImpl;
import br.com.postech.mixfast.dataproviders.model.rest.pagamento.PagamentoApiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final IPagamentoClient pagamentoClient;
    private final TokenGatewayImpl tokenGatewayImpl;

    @Override
    public String gerarQrCode(Pedido pedido) {
        PagamentoApiRequest pagamentoApiRequest = PagamentoApiRequest.builder()
                .pedido(pedido.getCodigo())
                .valorTotal(pedido.getValorTotal())
                .build();

        String token = tokenGatewayImpl.gerarToken();

        return pagamentoClient.gerarQRCode(pagamentoApiRequest, token);
    }
}
