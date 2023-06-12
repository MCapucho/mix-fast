package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoBuscarPorCodigoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoDeletarPorCodigoUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private ProdutoDeletarPorCodigoUseCaseImpl produtoDeletarPorCodigoUseCaseImpl;
    @Mock
    private ProdutoGateway produtoGateway;
    @Mock
    private ProdutoBuscarPorCodigoUseCase produtoBuscarPorCodigoUseCase;

    private Produto produtoResponse;

    @BeforeEach
    void setUp() {
        produtoResponse = Produto.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Produto Teste")
                .descricao("Produto Teste Descrição")
                .preco(new BigDecimal(0.00))
                .categoria(Categoria.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .build();
    }

    @Test
    void deveDeletarUmProdutoComSucesso() {
        when(produtoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(produtoResponse);

        doNothing().when(produtoGateway)
                .deletarPorCodigo(CODIGO);

        produtoDeletarPorCodigoUseCaseImpl.deletarPorCodigo(CODIGO);

        verify(produtoBuscarPorCodigoUseCase, times(1)).buscarPorCodigo(CODIGO);
        verify(produtoGateway, times(1)).deletarPorCodigo(CODIGO);
    }
}