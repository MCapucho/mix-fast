package br.com.postech.mixfast.core.usecase.impl.categoria;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.exception.categoria.CategoriaNotFoundException;
import br.com.postech.mixfast.core.gateway.CategoriaGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaBuscarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoriaBuscarPorCodigoUseCaseImpl implements CategoriaBuscarPorCodigoUseCase {

    private final CategoriaGateway categoriaGateway;

    @Override
    public Categoria buscarPorCodigo(String codigo) {
        Categoria categoria = categoriaGateway.buscarPorCodigo(codigo);

        if (categoria == null) {
            throw new CategoriaNotFoundException("Categoria não encontrada com o código informado");
        }

        return categoria;
    }
}
