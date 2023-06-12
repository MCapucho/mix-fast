package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.exception.produto.ProdutoListEmptyException;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoBuscarPorCategoriaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProdutoBuscarPorCategoriaUseCaseImpl implements ProdutoBuscarPorCategoriaUseCase {

    private final ProdutoGateway produtoGateway;

    @Override
    public List<Produto> buscarPorCategoria(String categoria) {
        List<Produto> listaProdutos = produtoGateway.buscarPorCategoria(categoria);

        if (listaProdutos.isEmpty()) {
            throw new ProdutoListEmptyException("Lista de produtos em branco");
        }

        return listaProdutos;
    }
}
