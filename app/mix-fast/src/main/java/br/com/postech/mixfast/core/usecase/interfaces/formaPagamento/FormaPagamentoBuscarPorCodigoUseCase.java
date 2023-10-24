package br.com.postech.mixfast.core.usecase.interfaces.formaPagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;

public interface FormaPagamentoBuscarPorCodigoUseCase {

    FormaPagamento buscarPorCodigo(String codigo);
}
