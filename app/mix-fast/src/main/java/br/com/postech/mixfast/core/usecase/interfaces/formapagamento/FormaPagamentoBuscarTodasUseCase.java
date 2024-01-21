package br.com.postech.mixfast.core.usecase.interfaces.formapagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;

import java.util.List;

public interface FormaPagamentoBuscarTodasUseCase {

    List<FormaPagamento> buscarTodas();
}
