package br.com.postech.mixfast.core.usecase.interfaces.produto;

import br.com.postech.mixfast.core.entity.Produto;

public interface ProdutoBuscarPorCodigoUseCase {

    Produto buscarPorCodigo(String codigo);
}
