package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoFailedException;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoCadastrarUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoCadastrarUseCaseImpl implements PedidoCadastrarUseCase {

    private final PedidoGateway pedidoGateway;

    @Override
    public Pedido cadastrar(Pedido pedido) {
        if (StringUtils.isBlank(pedido.getCliente().getCodigo())) {
            pedido.getCliente().setCodigo("0000000000");
        }

        Pedido pedidoCadastrado = pedidoGateway.cadastrar(pedido);

        if (pedidoCadastrado.getFila() == null) {
            throw new PedidoFailedException("Erro ao cadastrar o pedido informado");
        }

        return pedidoCadastrado;
    }
}
