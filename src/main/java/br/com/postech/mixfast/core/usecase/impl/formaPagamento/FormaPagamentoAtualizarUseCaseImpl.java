package br.com.postech.mixfast.core.usecase.impl.formaPagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.FormaPagamentoAtualizarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FormaPagamentoAtualizarUseCaseImpl implements FormaPagamentoAtualizarUseCase {

    private final FormaPagamentoGateway formaPagamentoGateway;

    @Override
    public FormaPagamento atualizar(String codigo, FormaPagamento formaPagamento) {
        return null;
    }
}
