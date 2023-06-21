package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Produto;
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
        categoriaBuscarPorCodigoUseCase.buscarPorCodigo(produto.getCategoria().getCodigo());

        Produto produtoEncontrado = produtoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        produtoEncontrado.setNome(produto.getNome() != null ?
                produto.getNome() : produtoEncontrado.getNome());
        produtoEncontrado.setDescricao(produto.getDescricao() != null ?
                produto.getDescricao() : produtoEncontrado.getDescricao());
        produtoEncontrado.setPreco(produto.getPreco() != null ?
                produto.getPreco() : produtoEncontrado.getPreco());

        return produtoGateway.cadastrarOuAtualizar(produtoEncontrado);
    }
}
