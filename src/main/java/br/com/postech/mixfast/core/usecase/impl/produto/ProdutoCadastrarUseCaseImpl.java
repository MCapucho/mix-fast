package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.exception.produto.ProdutoBadRequestException;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoCadastrarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProdutoCadastrarUseCaseImpl implements ProdutoCadastrarUseCase {

    private final ProdutoGateway produtoGateway;
    private final CategoriaBuscarPorCodigoUseCase categoriaBuscarPorCodigoUseCase;

    @Override
    public Produto cadastrar(Produto produto) {
        categoriaBuscarPorCodigoUseCase.buscarPorCodigo(produto.getCategoria().getCodigo());

        Produto produtoCadastrado = produtoGateway.cadastrarOuAtualizar(produto);

        if (produtoCadastrado == null) {
            throw new ProdutoBadRequestException("Cadastro de produto não foi concluído");
        }

        return produtoCadastrado;
    }
}
