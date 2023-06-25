package br.com.postech.mixfast.core.gateway;

import br.com.postech.mixfast.core.entity.Categoria;

import java.util.List;

public interface CategoriaGateway {

    Categoria cadastrarOuAtualizar(Categoria categoria);
    List<Categoria> buscarTodas();
    Categoria buscarPorCodigo(String codigo);
    void deletarPorCodigo(String codigo);
    Boolean encontrarPorNome(String nome);
}
