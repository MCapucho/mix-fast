package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.exception.produto.ProdutoNotFoundException;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoBuscarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProdutoBuscarPorCodigoUseCaseImpl implements ProdutoBuscarPorCodigoUseCase {

    private final ProdutoGateway produtoGateway;

    @Override
    public Produto buscarPorCodigo(String codigo) {
        Produto produto = produtoGateway.buscarPorCodigo(codigo);

        if (produto == null) {
            throw new ProdutoNotFoundException("Produto não encontrado com o código informado");
        }

        return produto;
    }
}
