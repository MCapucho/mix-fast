package br.com.postech.mixfast.core.usecase.interfaces.formaPagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;

public interface FormaPagamentoAtualizarUseCase {

    FormaPagamento atualizar(String codigo, FormaPagamento formaPagamento);
}
