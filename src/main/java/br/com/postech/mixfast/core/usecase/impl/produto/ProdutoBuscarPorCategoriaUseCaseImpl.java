package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.exception.produto.ProdutoListEmptyException;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoBuscarPorCategoriaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProdutoBuscarPorCategoriaUseCaseImpl implements ProdutoBuscarPorCategoriaUseCase {

    private final ProdutoGateway produtoGateway;
    private final CategoriaBuscarPorCodigoUseCase categoriaBuscarPorCodigoUseCase;

    @Override
    public List<Produto> buscarPorCategoria(String categoria) {
        categoriaBuscarPorCodigoUseCase.buscarPorCodigo(categoria);

        List<Produto> listaProdutos = produtoGateway.buscarPorCategoria(categoria);

        if (listaProdutos.isEmpty()) {
            throw new ProdutoListEmptyException("Lista de produtos em branco");
        }

        return listaProdutos;
    }
}
