package br.com.postech.mixfast.dataproviders.gateway.api;

import br.com.postech.mixfast.core.entity.*;
import br.com.postech.mixfast.core.entity.enums.StatusPagamento;
import br.com.postech.mixfast.core.entity.enums.StatusPedido;
import br.com.postech.mixfast.dataproviders.model.rest.DadosPagamentoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoGatewayImplTest {

    @InjectMocks
    private PagamentoGatewayImpl pagamentoGatewayImpl;
    @Mock
    private IPagamentoClient pagamentoClient;

    private Pedido pedido;
    private DadosPagamentoResponse dadosPagamentoResponse;

    @BeforeEach
    void setUp() {
        pedido = Pedido.builder()
                .cliente(Cliente.builder()
                        .codigo(UUID.randomUUID().toString())
                        .nome("Cliente Teste")
                        .build())
                .formaPagamento(FormaPagamento.builder()
                        .codigo(UUID.randomUUID().toString())
                        .descricao("QR Code")
                        .build())
                .itens(List.of(PedidoProduto.builder()
                        .produto(Produto.builder()
                                .codigo(UUID.randomUUID().toString())
                                .nome("Produto Teste")
                                .preco(new BigDecimal("10.00"))
                                .categoria(Categoria.builder()
                                        .codigo(UUID.randomUUID().toString())
                                        .nome("Categoria Teste")
                                        .build())
                                .build())
                        .quantidade(1)
                        .build()))
                .statusPedido(StatusPedido.RECEBIDO)
                .statusPagamento(StatusPagamento.AGUARDANDO)
                .build();

        dadosPagamentoResponse = DadosPagamentoResponse.builder()
                .qrDados("abc123")
                .build();
    }

    @Test
    void gerarQrCode() {
        when(pagamentoClient.gerarQRCode(any(), anyString()))
                .thenReturn(dadosPagamentoResponse);

        String qrCodeGerado = pagamentoGatewayImpl.gerarQrCode(pedido);

        Assertions.assertEquals("abc123", qrCodeGerado);
    }
}