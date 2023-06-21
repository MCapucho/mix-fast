package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoFailedException;
import br.com.postech.mixfast.core.gateway.PagamentoGateway;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoEnviarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoEnviarUseCaseImpl implements PedidoEnviarUseCase {

    private final PedidoGateway pedidoGateway;
    private final PagamentoGateway pagamentoGateway;

    @Override
    public Pedido enviar(Pedido pedido) {
        Pedido pedidoEnviado = pedidoGateway.enviar(pedido);

        String qrCode = pagamentoGateway.pagarQrCode(pedidoEnviado);
        pedidoEnviado.setQrCode(qrCode);

        if (pedidoEnviado.getFila() == null) {
            throw new PedidoFailedException("Erro ao cadastrar o pedido informado");
        }

        return pedidoEnviado;
    }
}
