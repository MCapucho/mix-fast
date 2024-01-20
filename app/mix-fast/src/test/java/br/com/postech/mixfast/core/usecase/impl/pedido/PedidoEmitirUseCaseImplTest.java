package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoFailedException;
import br.com.postech.mixfast.core.gateway.PagamentoGateway;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.FormaPagamentoBuscarPorCodigoUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoEmitirUseCaseImplTest {

    @InjectMocks
    private PedidoEmitirUseCaseImpl pedidoEmitirUseCaseImpl;
    @Mock
    private PedidoGateway pedidoGateway;
    @Mock
    private PagamentoGateway pagamentoGateway;
    @Mock
    private ClienteBuscarPorCodigoUseCase clienteBuscarPorCodigoUseCase;
    @Mock
    private FormaPagamentoBuscarPorCodigoUseCase formaPagamentoBuscarPorCodigoUseCase;

    private Cliente cliente;
    private FormaPagamento formaPagamento;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .codigo(UUID.randomUUID().toString())
                .build();

        formaPagamento = FormaPagamento.builder()
                .codigo(UUID.randomUUID().toString())
                .descricao("Dinheiro")
                .build();

        pedido = Pedido.builder()
                .codigo(UUID.randomUUID().toString())
                .formaPagamento(formaPagamento)
                .cliente(cliente)
                .fila(1)
                .build();
    }

    @Test
    void deveEmitirUmPedidoComSucesso_Dinheiro() {
        when(clienteBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(cliente);

        when(formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(formaPagamento);

        when(pedidoGateway.emitir(any(Pedido.class)))
                .thenReturn(pedido);

        Pedido pedidoEmitido = pedidoEmitirUseCaseImpl.emitir(pedido);

        Assertions.assertNotNull(pedidoEmitido);
        Assertions.assertEquals(1, pedidoEmitido.getFila());
    }

    @Test
    void deveEmitirUmPedidoComSucesso_QRCode() {
        formaPagamento.setDescricao("QR Code");
        pedido.setFormaPagamento(formaPagamento);

        when(clienteBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(cliente);

        when(formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(formaPagamento);

        when(pedidoGateway.emitir(any(Pedido.class)))
                .thenReturn(pedido);

        when(pagamentoGateway.gerarQrCode(any(Pedido.class)))
                .thenReturn("abc1234");

        Pedido pedidoEmitido = pedidoEmitirUseCaseImpl.emitir(pedido);

        Assertions.assertNotNull(pedidoEmitido);
        Assertions.assertEquals(1, pedidoEmitido.getFila());
    }

    @Test
    void deveEmitirUmPedidoComFalha_QRCode() {
        formaPagamento.setDescricao("QR Code");
        pedido.setFormaPagamento(formaPagamento);
        pedido.setFila(null);

        when(clienteBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(cliente);

        when(formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(formaPagamento);

        when(pedidoGateway.emitir(any(Pedido.class)))
                .thenReturn(pedido);

        when(pagamentoGateway.gerarQrCode(any(Pedido.class)))
                .thenReturn("abc1234");

        Exception exception = Assertions.assertThrows(PedidoFailedException.class, () ->
                pedidoEmitirUseCaseImpl.emitir(pedido));

        String mensagemEsperada = "Erro ao emitir o pedido informado";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}