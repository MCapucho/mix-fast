package br.com.postech.mixfast.dataproviders.gateway.api;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.gateway.PagamentoGateway;
import br.com.postech.mixfast.dataproviders.model.DadosPagamentoRequest;
import br.com.postech.mixfast.dataproviders.model.DadosPagamentoResponse;
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

    @Value("${feign.client.config.token}")
    private String token;

    private final IPagamentoClient pagamentoClient;

    @Override
    public String pagarQrCode(Pedido pedido) {
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
                            .unidade("un")
                            .valorTotal(element.getProduto().getPreco().multiply(new BigDecimal(element.getQuantidade())))
                            .build();

            listaDadosPagamentoItensRequest.add(dadosPagamentoItensRequest);
        });

        DadosPagamentoRequest.DadosPagamentoResponsavelRequest dadosPagamentoResponsavelRequest =
                DadosPagamentoRequest.DadosPagamentoResponsavelRequest.builder()
                        .codigo(6179356)
                        .build();

        DadosPagamentoRequest dadosPagamentoRequest = DadosPagamentoRequest.builder()
                .referenciaExterna(pedido.getCodigo())
                .descricao("Mix Fast")
                .titulo("Pedido Mix Fast")
                .valorTotal(pedido.getValorTotal())
                .itens(listaDadosPagamentoItensRequest)
                .responsavel(dadosPagamentoResponsavelRequest)
                .build();

        String accessToken = PREFIX_TOKEN + token;

        DadosPagamentoResponse dadosPagamentoResponse = pagamentoClient.gerarQRCode(dadosPagamentoRequest, accessToken);

        return dadosPagamentoResponse.getQrDados();
    }
}
