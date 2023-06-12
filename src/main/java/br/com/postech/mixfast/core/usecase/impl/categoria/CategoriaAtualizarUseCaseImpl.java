package br.com.postech.mixfast.core.usecase.impl.categoria;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.gateway.CategoriaGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaAtualizarUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaBuscarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoriaAtualizarUseCaseImpl implements CategoriaAtualizarUseCase {

    private final CategoriaGateway categoriaGateway;
    private final CategoriaBuscarPorCodigoUseCase categoriaBuscarPorCodigoUseCase;

    @Override
    public Categoria atualizar(String codigo, Categoria categoria) {
        Categoria categoriaEncontrada = categoriaBuscarPorCodigoUseCase.buscarPorCodigo(codigo);

        categoriaEncontrada.setNome(categoria.getNome() != null ? categoria.getNome() : categoriaEncontrada.getNome());

        return categoriaGateway.cadastrarOuAtualizar(categoriaEncontrada);
    }
}
