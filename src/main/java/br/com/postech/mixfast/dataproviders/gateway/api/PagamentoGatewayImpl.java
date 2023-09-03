package br.com.postech.mixfast.dataproviders.gateway.api;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.gateway.PagamentoGateway;
import br.com.postech.mixfast.dataproviders.model.rest.DadosPagamentoRequest;
import br.com.postech.mixfast.dataproviders.model.rest.DadosPagamentoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PagamentoGatewayImpl implements PagamentoGateway {

    private static final String PREFIX_TOKEN = "Bearer ";
    private static final String UNIDADE = "un";
    private static final String URL_NOTIFICACAO = "https://webhook.site/8a357810-a318-40b0-9fb4-71ee1cf1f27e";

    @Value("${spring.application.name}")
    private String aplicacao;

    @Value("${feign.client.config.token}")
    private String token;

    private final IPagamentoClient pagamentoClient;

    @Override
    public String gerarQrCode(Pedido pedido) {
        List<DadosPagamentoRequest.DadosPagamentoItensRequest> listaDadosPagamentoItensRequest = new ArrayList<>();

        pedido.getItens().forEach(element -> {
            DadosPagamentoRequest.DadosPagamentoItensRequest dadosPagamentoItensRequest =
                    DadosPagamentoRequest.DadosPagamentoItensRequest.builder()
                            .codigo(element.getProduto().getCodigo())
                            .categoria(element.getProduto().getCategoria().getNome())
                            .titulo(element.getProduto().getNome())
                            .descricao(element.getProduto().getDescricao())
                            .precoUnitario(element.getProduto().getPreco())
                            .quantidade(element.getQuantidade())
                            .unidade(UNIDADE)
                            .valorTotal(element.getProduto().getPreco().multiply(new BigDecimal(element.getQuantidade())))
                            .build();

            listaDadosPagamentoItensRequest.add(dadosPagamentoItensRequest);
        });

        DadosPagamentoRequest dadosPagamentoRequest = DadosPagamentoRequest.builder()
                .referenciaExterna(pedido.getCodigo())
                .descricao(aplicacao)
                .titulo(aplicacao)
                .urlNotificacao(URL_NOTIFICACAO)
                .valorTotal(pedido.getValorTotal())
                .itens(listaDadosPagamentoItensRequest)
                .build();

        String accessToken = PREFIX_TOKEN + token;

        DadosPagamentoResponse dadosPagamentoResponse = pagamentoClient.gerarQRCode(dadosPagamentoRequest, accessToken);

        return dadosPagamentoResponse.getQrDados();
    }
}
