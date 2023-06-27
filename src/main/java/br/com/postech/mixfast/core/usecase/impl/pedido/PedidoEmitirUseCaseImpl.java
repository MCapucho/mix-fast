package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoFailedException;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoEmitirUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoEmitirUseCaseImpl implements PedidoEmitirUseCase {

    private final PedidoGateway pedidoGateway;

    @Override
    public Pedido emitir(Pedido pedido) {
        Pedido pedidoEmitido = pedidoGateway.emitir(pedido);

        if (pedidoEmitido.getFila() == null) {
            throw new PedidoFailedException("Erro ao emitir o pedido informado");
        }

        return pedidoEmitido;
    }
}
