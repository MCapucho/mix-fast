package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.exception.produto.ProdutoDuplicatedException;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoAtualizarUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoBuscarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProdutoAtualizarUseCaseImpl implements ProdutoAtualizarUseCase {

    private final ProdutoGateway produtoGateway;
    private final ProdutoBuscarPorCodigoUseCase produtoBuscarPorCodigoUseCase;
    private final CategoriaBuscarPorCodigoUseCase categoriaBuscarPorCodigoUseCase;

    @Override
    public Produto atualizar(String codigo, Produto produto) {
        Produto produtoEncontrado = produtoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);

        if (produtoGateway.encontrarPorNome(produto.getNome())) {
            throw new ProdutoDuplicatedException("Produto informado já cadastrado");
        }

        Categoria categoria = null;

        if (produto.getCategoria() != null) {
            categoria = categoriaBuscarPorCodigoUseCase.buscarPorCodigo(produto.getCategoria().getCodigo());
        }

        produtoEncontrado.setNome(produto.getNome() != null ?
                produto.getNome() : produtoEncontrado.getNome());
        produtoEncontrado.setDescricao(produto.getDescricao() != null ?
                produto.getDescricao() : produtoEncontrado.getDescricao());
        produtoEncontrado.setPreco(produto.getPreco() != null ?
                produto.getPreco() : produtoEncontrado.getPreco());
        produtoEncontrado.setCategoria(produto.getCategoria() != null ?
                categoria : produtoEncontrado.getCategoria());

        return produtoGateway.cadastrarOuAtualizar(produtoEncontrado);
    }
}
