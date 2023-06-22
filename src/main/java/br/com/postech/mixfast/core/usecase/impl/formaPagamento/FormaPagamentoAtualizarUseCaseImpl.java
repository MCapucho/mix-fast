package br.com.postech.mixfast.core.usecase.impl.formaPagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.FormaPagamentoAtualizarUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.FormaPagamentoBuscarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FormaPagamentoAtualizarUseCaseImpl implements FormaPagamentoAtualizarUseCase {

    private final FormaPagamentoGateway formaPagamentoGateway;
    private final FormaPagamentoBuscarPorCodigoUseCase formaPagamentoBuscarPorCodigoUseCase;

    @Override
    public FormaPagamento atualizar(String codigo, FormaPagamento formaPagamento) {
        FormaPagamento formaPagamentoEncontrada = formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);

        formaPagamentoEncontrada.setDescricao(formaPagamento.getDescricao() != null ?
                formaPagamento.getDescricao() :
                formaPagamentoEncontrada.getDescricao());

        return formaPagamentoGateway.cadastrarOuAtualizar(formaPagamentoEncontrada);
    }
}
