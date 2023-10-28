package br.com.postech.mixfast.core.gateway;

import br.com.postech.mixfast.core.entity.Produto;

import java.util.List;

public interface ProdutoGateway {

    Produto cadastrarOuAtualizar(Produto produto);
    List<Produto> buscarTodos();
    Produto buscarPorCodigo(String codigo);
    void deletarPorCodigo(String codigo);
    List<Produto> buscarPorCategoria(String categoria);
    Boolean encontrarPorNome(String nome);
}
