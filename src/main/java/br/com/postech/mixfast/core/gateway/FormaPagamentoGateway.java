package br.com.postech.mixfast.core.gateway;

import br.com.postech.mixfast.core.entity.FormaPagamento;

import java.util.List;

public interface FormaPagamentoGateway {

    FormaPagamento cadastrarOuAtualizar(FormaPagamento formaPagamento);
    List<FormaPagamento> buscarTodas();
    FormaPagamento buscarPorCodigo(String codigo);
    void deletarPorCodigo(String codigo);
    Boolean encontrarPorDescricao(String descricao);
}
