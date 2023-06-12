package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.exception.produto.ProdutoBadRequestException;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoCadastrarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProdutoCadastrarUseCaseImpl implements ProdutoCadastrarUseCase {

    private final ProdutoGateway produtoGateway;

    @Override
    public Produto cadastrar(Produto produto) {
        Produto produtoCadastrado = produtoGateway.cadastrarOuAtualizar(produto);

        if (produto == null) {
            throw new ProdutoBadRequestException("Cadastro de cliente não foi concluído");
        }

        return produtoCadastrado;
    }
}
