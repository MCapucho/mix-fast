package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoFailedException;
import br.com.postech.mixfast.core.gateway.PagamentoGateway;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoCadastrarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoCadastrarUseCaseImpl implements PedidoCadastrarUseCase {

    private final PedidoGateway pedidoGateway;
    private final PagamentoGateway pagamentoGateway;

    @Override
    public Pedido cadastrar(Pedido pedido) {
        Pedido pedidoCadastrado = pedidoGateway.cadastrar(pedido);

        String qrCode = pagamentoGateway.pagarQrCode(pedidoCadastrado);
        pedidoCadastrado.setQrCode(qrCode);

        if (pedidoCadastrado.getFila() == null) {
            throw new PedidoFailedException("Erro ao cadastrar o pedido informado");
        }

        return pedidoCadastrado;
    }
}
