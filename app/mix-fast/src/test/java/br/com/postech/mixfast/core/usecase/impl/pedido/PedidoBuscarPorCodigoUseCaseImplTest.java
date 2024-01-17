package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoNotFoundException;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoBuscarPorCodigoUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private PedidoBuscarPorCodigoUseCaseImpl pedidoBuscarPorCodigoUseCaseImpl;
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
    void deveBuscarPorCodigoPedidoComSucesso() {
        when(pedidoGateway.buscarPorCodigo(anyString()))
                .thenReturn(pedido);

        Pedido pedidoEncontrado = pedidoBuscarPorCodigoUseCaseImpl.buscarPorCodigo(CODIGO);

        Assertions.assertNotNull(pedidoEncontrado);
    }

    @Test
    void naoDeveBuscarPorCodigoPedido_Erro() {
        Exception exception = Assertions.assertThrows(PedidoNotFoundException.class, () ->
                pedidoBuscarPorCodigoUseCaseImpl.buscarPorCodigo(CODIGO));

        String mensagemEsperada = "Pedido não encontrado com o código informado";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}