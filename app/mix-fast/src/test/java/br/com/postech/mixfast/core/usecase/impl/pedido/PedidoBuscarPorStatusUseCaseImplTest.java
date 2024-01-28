package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.entity.enums.StatusPedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoListEmptyException;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoBuscarPorStatusUseCaseImplTest {

    @InjectMocks
    private PedidoBuscarPorStatusUseCaseImpl pedidoBuscarPorStatusUseCaseImpl;
    @Mock
    private PedidoGateway pedidoGateway;

    private Pedido pedidoResponse;

    @BeforeEach
    void setUp() {
        pedidoResponse = Pedido.builder()
                .codigo(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void deveBuscarTodosPedidosComSucesso_Recebido() {
        when(pedidoGateway.buscarPorStatusPedido(anyString()))
                .thenReturn(List.of(pedidoResponse));

        List<Pedido> listaPedidos = pedidoBuscarPorStatusUseCaseImpl.buscarPorStatusPedido(StatusPedido.RECEBIDO.name());

        Assertions.assertNotNull(listaPedidos);
        Assertions.assertEquals(1, listaPedidos.size());
    }

    @Test
    void naoDeveBuscarTodosPedidos_Erro() {
        Exception exception = Assertions.assertThrows(PedidoListEmptyException.class, () ->
                pedidoBuscarPorStatusUseCaseImpl.buscarPorStatusPedido(StatusPedido.RECEBIDO.name()));

        String mensagemEsperada = "Lista de pedidos por status RECEBIDO em branco";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}