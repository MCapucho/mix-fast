package br.com.postech.mixfast.core.usecase.impl.formaPagamento;

import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.FormaPagamentoBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.FormaPagamentoDeletarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FormaPagamentoDeletarPorCodigoUseCaseImpl implements FormaPagamentoDeletarPorCodigoUseCase {

    private final FormaPagamentoGateway formaPagamentoGateway;
    private final FormaPagamentoBuscarPorCodigoUseCase formaPagamentoBuscarPorCodigoUseCase;

    @Override
    public void deletarPorCodigo(String codigo) {
        formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        formaPagamentoGateway.deletarPorCodigo(codigo);
    }
}