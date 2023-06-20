package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoNotFoundException;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoBuscarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoBuscarPorCodigoUseCaseImpl implements PedidoBuscarPorCodigoUseCase {

    private final PedidoGateway pedidoGateway;

    @Override
    public Pedido buscarPorCodigo(String codigo) {
        Pedido pedido = pedidoGateway.buscarPorCodigo(codigo);

        if (pedido == null) {
            throw new PedidoNotFoundException("Pedido não encontrado com o código informado");
        }

        return pedido;
    }
}
