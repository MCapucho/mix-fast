package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoListEmptyException;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoBuscarTodosUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PedidoBuscarTodosUseCaseImpl implements PedidoBuscarTodosUseCase {

    private final PedidoGateway pedidoGateway;

    @Override
    public List<Pedido> buscarTodos() {
        List<Pedido> listaPedido = pedidoGateway.buscarTodos();

        if (listaPedido.isEmpty()) {
            throw new PedidoListEmptyException("Lista de pedidos em branco");
        }

        return listaPedido;
    }
}
