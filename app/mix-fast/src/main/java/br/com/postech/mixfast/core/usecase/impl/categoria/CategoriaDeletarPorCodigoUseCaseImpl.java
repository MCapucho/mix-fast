package br.com.postech.mixfast.core.usecase.impl.categoria;

import br.com.postech.mixfast.core.gateway.CategoriaGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaDeletarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoriaDeletarPorCodigoUseCaseImpl implements CategoriaDeletarPorCodigoUseCase {

    private final CategoriaGateway categoriaGateway;
    private final CategoriaBuscarPorCodigoUseCase categoriaBuscarPorCodigoUseCase;

    @Override
    public void deletarPorCodigo(String codigo) {
        categoriaBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        categoriaGateway.deletarPorCodigo(codigo);
    }
}
