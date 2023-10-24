package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoDeletarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProdutoDeletarPorCodigoUseCaseImpl implements ProdutoDeletarPorCodigoUseCase {

    private final ProdutoGateway produtoGateway;
    private final ProdutoBuscarPorCodigoUseCase produtoBuscarPorCodigoUseCase;

    @Override
    public void deletarPorCodigo(String codigo) {
        produtoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        produtoGateway.deletarPorCodigo(codigo);
    }
}
