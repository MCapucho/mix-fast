package br.com.postech.mixfast.core.gateway;

import br.com.postech.mixfast.core.entity.FormaPagamento;

public interface FormaPagamentoGateway {

    FormaPagamento cadastrar(FormaPagamento formaPagamento);
    Boolean encontrarPorDescricao(String descricao);
}
