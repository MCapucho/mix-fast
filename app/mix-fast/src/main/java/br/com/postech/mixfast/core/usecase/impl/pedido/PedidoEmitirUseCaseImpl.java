package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoFailedException;
import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import br.com.postech.mixfast.core.gateway.PagamentoGateway;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.gateway.ProducerNotificationGateway;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoEmitirUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoEmitirUseCaseImpl implements PedidoEmitirUseCase {

    @Value("${aws.queue.name.pedido}")
    private String queuePedido;

    private static final String QR_CODE = "Code";

    private final PedidoGateway pedidoGateway;
    private final PagamentoGateway pagamentoGateway;
    private final ProducerNotificationGateway producerNotificationGateway;

    private final FormaPagamentoGateway formaPagamentoGateway;
    private final ClienteBuscarPorCodigoUseCase clienteBuscarPorCodigoUseCase;

    @Override
    public Pedido emitir(Pedido pedido) {
        Cliente cliente = null;

        if (pedido.getCliente() != null) {
            cliente = clienteBuscarPorCodigoUseCase.buscarPorCodigo(pedido.getCliente().getCodigo());
        }

        String formaPagamentoDescricao =
                formaPagamentoGateway.buscarPorCodigo(pedido.getFormaPagamento());

        Pedido pedidoEmitido = pedidoGateway.emitir(pedido);

        if (formaPagamentoDescricao.contains(QR_CODE)) {
            String qrCode = pagamentoGateway.gerarQrCode(pedidoEmitido);
            pedidoEmitido.setQrCode(qrCode);
        }

        if (pedidoEmitido.getFila() == null) {
            throw new PedidoFailedException("Erro ao emitir o pedido informado");
        }

        producerNotificationGateway.notificarPedido(pedidoEmitido, cliente, queuePedido);
        return pedidoEmitido;
    }
}
