package br.com.postech.mixfast.core.usecase.impl.webhook;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.entity.enums.StatusPagamento;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoBuscarPorCodigoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebhookUseCaseImplTest {

    private static final String CODIGO_PEDIDO = UUID.randomUUID().toString();
    private static final String CODIGO_STATUS_PAGAMENTO_APROVADO = StatusPagamento.APROVADO.name();
    private static final String CODIGO_STATUS_PAGAMENTO_REPROVADO = StatusPagamento.REPROVADO.name();

    @InjectMocks
    private WebhookUseCaseImpl webhookUseCaseImpl;
    @Mock
    private PedidoBuscarPorCodigoUseCase pedidoBuscarPorCodigoUseCase;
    @Mock
    private PedidoGateway pedidoGateway;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = Pedido.builder()
                .codigo(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void deveAtualizarUmPedidoComSucesso_StatusPagamentoAprovado() {
        when(pedidoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(pedido);

        doNothing().when(pedidoGateway)
                .atualizarStatusPagamento(pedido);

        webhookUseCaseImpl.atualizar(CODIGO_PEDIDO, CODIGO_STATUS_PAGAMENTO_APROVADO);

        verify(pedidoBuscarPorCodigoUseCase, times(1)).buscarPorCodigo(CODIGO_PEDIDO);
        verify(pedidoGateway, times(1)).atualizarStatusPagamento(pedido);
    }

    @Test
    void deveAtualizarUmPedidoComSucesso_StatusPagamentoReprovado() {
        when(pedidoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(pedido);

        doNothing().when(pedidoGateway)
                .atualizarStatusPagamento(pedido);

        webhookUseCaseImpl.atualizar(CODIGO_PEDIDO, CODIGO_STATUS_PAGAMENTO_REPROVADO);

        verify(pedidoBuscarPorCodigoUseCase, times(1)).buscarPorCodigo(CODIGO_PEDIDO);
        verify(pedidoGateway, times(1)).atualizarStatusPagamento(pedido);
    }
}