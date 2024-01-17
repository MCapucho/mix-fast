package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.entity.enums.StatusPedido;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoBuscarPorCodigoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoAtualizarStatusUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private PedidoAtualizarStatusUseCaseImpl pedidoAtualizarStatusUseCaseImpl;
    @Mock
    private PedidoBuscarPorCodigoUseCase pedidoBuscarPorCodigoUseCase;
    @Mock
    private PedidoGateway pedidoGateway;

    private Pedido pedido;

    @Test
    void deveAtualizarPedidoComSucesso_StatusPreparar() {
        pedido = Pedido.builder()
                .codigo(UUID.randomUUID().toString())
                .statusPedido(StatusPedido.RECEBIDO)
                .build();

        when(pedidoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(pedido);

        doNothing().when(pedidoGateway)
                .atualizarStatusPedido(pedido);

        pedidoAtualizarStatusUseCaseImpl.preparar(CODIGO);

        verify(pedidoBuscarPorCodigoUseCase, times(1)).buscarPorCodigo(CODIGO);
        verify(pedidoGateway, times(1)).atualizarStatusPedido(pedido);
    }

    @Test
    void deveAtualizarPedidoComSucesso_StatusEntregar() {
        pedido = Pedido.builder()
                .codigo(UUID.randomUUID().toString())
                .statusPedido(StatusPedido.PREPARANDO)
                .build();

        when(pedidoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(pedido);

        doNothing().when(pedidoGateway)
                .atualizarStatusPedido(pedido);

        pedidoAtualizarStatusUseCaseImpl.entregar(CODIGO);

        verify(pedidoBuscarPorCodigoUseCase, times(1)).buscarPorCodigo(CODIGO);
        verify(pedidoGateway, times(1)).atualizarStatusPedido(pedido);
    }

    @Test
    void deveAtualizarPedidoComSucesso_StatusFinalizar() {
        pedido = Pedido.builder()
                .codigo(UUID.randomUUID().toString())
                .statusPedido(StatusPedido.ENTREGUE)
                .build();

        when(pedidoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(pedido);

        doNothing().when(pedidoGateway)
                .atualizarStatusPedido(pedido);

        pedidoAtualizarStatusUseCaseImpl.finalizar(CODIGO);

        verify(pedidoBuscarPorCodigoUseCase, times(1)).buscarPorCodigo(CODIGO);
        verify(pedidoGateway, times(1)).atualizarStatusPedido(pedido);
    }

    @Test
    void deveAtualizarPedidoComSucesso_StatusCancelar() {
        pedido = Pedido.builder()
                .codigo(UUID.randomUUID().toString())
                .statusPedido(StatusPedido.RECEBIDO)
                .build();

        when(pedidoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(pedido);

        doNothing().when(pedidoGateway)
                .atualizarStatusPedido(pedido);

        pedidoAtualizarStatusUseCaseImpl.cancelar(CODIGO);

        verify(pedidoBuscarPorCodigoUseCase, times(1)).buscarPorCodigo(CODIGO);
        verify(pedidoGateway, times(1)).atualizarStatusPedido(pedido);
    }
}