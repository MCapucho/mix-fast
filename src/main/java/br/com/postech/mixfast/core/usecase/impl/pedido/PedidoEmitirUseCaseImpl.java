package br.com.postech.mixfast.core.usecase.impl.pedido;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoFailedException;
import br.com.postech.mixfast.core.gateway.PagamentoGateway;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.FormaPagamentoBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoEmitirUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoEmitirUseCaseImpl implements PedidoEmitirUseCase {

    private static final String QR_CODE = "Code";

    private final PedidoGateway pedidoGateway;
    private final PagamentoGateway pagamentoGateway;
    private final ClienteBuscarPorCodigoUseCase clienteBuscarPorCodigoUseCase;
    private final FormaPagamentoBuscarPorCodigoUseCase formaPagamentoBuscarPorCodigoUseCase;

    @Override
    public Pedido emitir(Pedido pedido) {
        if (pedido.getCliente() != null) {
            clienteBuscarPorCodigoUseCase.buscarPorCodigo(pedido.getCliente().getCodigo());
        }

        FormaPagamento formaPagamento =
                formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(pedido.getFormaPagamento().getCodigo());

        Pedido pedidoEmitido = pedidoGateway.emitir(pedido);

        if (formaPagamento.getDescricao().contains(QR_CODE)) {
            String qrCode = pagamentoGateway.gerarQrCode(pedidoEmitido);
            pedidoEmitido.setQrCode(qrCode);
        }

        if (pedidoEmitido.getFila() == null) {
            throw new PedidoFailedException("Erro ao emitir o pedido informado");
        }

        return pedidoEmitido;
    }
}
