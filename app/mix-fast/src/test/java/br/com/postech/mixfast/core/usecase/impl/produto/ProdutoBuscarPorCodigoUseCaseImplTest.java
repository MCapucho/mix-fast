package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.exception.produto.ProdutoNotFoundException;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoBuscarPorCodigoUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private ProdutoBuscarPorCodigoUseCaseImpl produtoBuscarPorCodigoUseCaseImpl;
    @Mock
    private ProdutoGateway produtoGateway;

    private Produto produtoRequest;
    private Produto produtoResponse;

    @BeforeEach
    void setUp() {
        produtoRequest = Produto.builder()
                .nome("Produto Teste")
                .descricao("Produto Teste Descrição")
                .preco(new BigDecimal("0.00"))
                .categoria(Categoria.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .build();

        produtoResponse = Produto.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Produto Teste")
                .descricao("Produto Teste Descrição")
                .preco(new BigDecimal("0.00"))
                .categoria(Categoria.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .build();
    }

    @Test
    void deveBuscarPorCodigoProdutoComSucesso() {
        when(produtoGateway.buscarPorCodigo(anyString()))
                .thenReturn(produtoResponse);

        Produto produtoCadastrado = produtoBuscarPorCodigoUseCaseImpl.buscarPorCodigo(CODIGO);

        Assertions.assertNotNull(produtoCadastrado);
        Assertions.assertEquals(produtoRequest.getNome(), produtoCadastrado.getNome());
        Assertions.assertEquals(produtoRequest.getDescricao(), produtoCadastrado.getDescricao());
        Assertions.assertEquals(produtoRequest.getPreco(), produtoCadastrado.getPreco());
    }

    @Test
    void naoDeveBuscarPorCodigoProduto_Erro() {
        Exception exception = Assertions.assertThrows(ProdutoNotFoundException.class, () ->
                produtoBuscarPorCodigoUseCaseImpl.buscarPorCodigo(CODIGO));

        String mensagemEsperada = "Produto não encontrado com o código informado";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}