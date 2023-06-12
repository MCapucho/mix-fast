package br.com.postech.mixfast.core.usecase.interfaces.categoria;

import br.com.postech.mixfast.core.entity.Categoria;

public interface CategoriaAtualizarUseCase {

    Categoria atualizar(String codigo, Categoria categoria);
}
