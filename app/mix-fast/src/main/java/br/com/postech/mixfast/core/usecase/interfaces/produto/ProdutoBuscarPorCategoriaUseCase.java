package br.com.postech.mixfast.core.usecase.interfaces.produto;

import br.com.postech.mixfast.core.entity.Produto;

import java.util.List;

public interface ProdutoBuscarPorCategoriaUseCase {

    List<Produto> buscarPorCategoria(String categoria);
}
