package br.com.postech.mixfast.core.usecase.impl.categoria;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.exception.categoria.CategoriaListEmptyException;
import br.com.postech.mixfast.core.gateway.CategoriaGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaBuscarTodasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriaBuscarTodasUseCaseImpl implements CategoriaBuscarTodasUseCase {

    private final CategoriaGateway categoriaGateway;

    @Override
    public List<Categoria> buscarTodas() {
        List<Categoria> listaCategorias = categoriaGateway.buscarTodas();

        if (listaCategorias.isEmpty()) {
            throw new CategoriaListEmptyException("Lista de categorias em branco");
        }

        return listaCategorias;
    }
}
