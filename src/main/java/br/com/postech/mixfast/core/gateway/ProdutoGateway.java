package br.com.postech.mixfast.core.gateway;

import br.com.postech.mixfast.core.entity.Produto;

import java.util.List;

public interface ProdutoGateway {

    Produto cadastrarOuAtualizar(Produto produto);
    List<Produto> buscarTodos();
    Produto buscarPorCodigo(String codigo);
    void deletarPorCodigo(Produto produto);
    List<Produto> buscarPorCategoria(String categoria);
}
