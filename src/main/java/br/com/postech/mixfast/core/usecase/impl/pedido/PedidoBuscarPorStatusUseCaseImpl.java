package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoListEmptyException;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoBuscarPorStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PedidoBuscarPorStatusUseCaseImpl implements PedidoBuscarPorStatusUseCase {

    private final PedidoGateway pedidoGateway;

    @Override
    public List<Pedido> buscarPorStatus(String status) {
        List<Pedido> listaPedido = pedidoGateway.buscarPorStatus(status);

        if (listaPedido.isEmpty()) {
            throw new PedidoListEmptyException(String.format("Lista de pedidos por status %s em branco", status));
        }

        return listaPedido;
    }
}
