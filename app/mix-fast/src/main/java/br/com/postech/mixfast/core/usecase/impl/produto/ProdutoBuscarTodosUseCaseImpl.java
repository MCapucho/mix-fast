package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.exception.produto.ProdutoListEmptyException;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoBuscarTodosUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProdutoBuscarTodosUseCaseImpl implements ProdutoBuscarTodosUseCase {

    private final ProdutoGateway produtoGateway;

    @Override
    public List<Produto> buscarTodos() {
        List<Produto> listaProduto = produtoGateway.buscarTodos();

        if (listaProduto.isEmpty()) {
            throw new ProdutoListEmptyException("Lista de produtos em branco");
        }

        return listaProduto;
    }
}
