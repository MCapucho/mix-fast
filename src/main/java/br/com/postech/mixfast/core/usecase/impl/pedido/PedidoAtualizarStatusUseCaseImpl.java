package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoAtualizarStatusUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoBuscarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoAtualizarStatusUseCaseImpl implements PedidoAtualizarStatusUseCase {

    private final PedidoGateway pedidoGateway;
    private final PedidoBuscarPorCodigoUseCase pedidoBuscarPorCodigoUseCase;

    @Override
    public void preparar(String codigo) {
        Pedido pedido = pedidoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        pedido.preparar();
        pedidoGateway.atualizarStatusPedido(pedido);
    }

    @Override
    public void entregar(String codigo) {
        Pedido pedido = pedidoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        pedido.entregar();
        pedidoGateway.atualizarStatusPedido(pedido);
    }

    @Override
    public void finalizar(String codigo) {
        Pedido pedido = pedidoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        pedido.finalizar();
        pedidoGateway.atualizarStatusPedido(pedido);
    }

    @Override
    public void cancelar(String codigo) {
        Pedido pedido = pedidoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        pedido.cancelar();
        pedidoGateway.atualizarStatusPedido(pedido);
    }
}
