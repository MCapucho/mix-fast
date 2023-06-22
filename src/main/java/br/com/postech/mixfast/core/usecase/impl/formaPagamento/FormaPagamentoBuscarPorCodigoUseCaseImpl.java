package br.com.postech.mixfast.core.usecase.impl.formaPagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.FormaPagamentoBuscarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FormaPagamentoBuscarPorCodigoUseCaseImpl implements FormaPagamentoBuscarPorCodigoUseCase {

    private final FormaPagamentoGateway formaPagamentoGateway;

    @Override
    public FormaPagamento buscarPorCodigo(String codigo) {
        return null;
    }
}
