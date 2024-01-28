package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.exception.produto.ProdutoDuplicatedException;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.produto.ProdutoBuscarPorCodigoUseCase;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoAtualizarUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private ProdutoAtualizarUseCaseImpl produtoAtualizarUseCaseImpl;
    @Mock
    private ProdutoGateway produtoGateway;
    @Mock
    private ProdutoBuscarPorCodigoUseCase produtoBuscarPorCodigoUseCase;
    @Mock
    private CategoriaBuscarPorCodigoUseCase categoriaBuscarPorCodigoUseCase;

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
    void deveAtualizarUmProdutoComSucesso() {
        when(categoriaBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(new Categoria());

        when(produtoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(produtoResponse);

        when(produtoGateway.cadastrarOuAtualizar(any()))
                .thenReturn(produtoResponse);

        Produto produtoAtualizado = produtoAtualizarUseCaseImpl.atualizar(CODIGO, produtoRequest);

        Assertions.assertNotNull(produtoAtualizado);
        Assertions.assertEquals(produtoRequest.getNome(), produtoAtualizado.getNome());
        Assertions.assertEquals(produtoRequest.getDescricao(), produtoAtualizado.getDescricao());
        Assertions.assertEquals(produtoRequest.getPreco(), produtoAtualizado.getPreco());
    }

    @Test
    void deveAtualizarUmProdutoComSucesso_NomeNull() {
        produtoRequest.setNome(null);

        when(produtoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(produtoResponse);

        when(produtoGateway.cadastrarOuAtualizar(any()))
                .thenReturn(produtoResponse);

        Produto produtoAtualizado = produtoAtualizarUseCaseImpl.atualizar(CODIGO, produtoRequest);

        Assertions.assertNotNull(produtoAtualizado);
    }

    @Test
    void deveAtualizarUmProdutoComSucesso_DescricaoNull() {
        produtoRequest.setDescricao(null);

        when(produtoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(produtoResponse);

        when(produtoGateway.cadastrarOuAtualizar(any()))
                .thenReturn(produtoResponse);

        Produto produtoAtualizado = produtoAtualizarUseCaseImpl.atualizar(CODIGO, produtoRequest);

        Assertions.assertNotNull(produtoAtualizado);
    }

    @Test
    void deveAtualizarUmProdutoComSucesso_PrecoNull() {
        produtoRequest.setPreco(null);

        when(produtoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(produtoResponse);

        when(produtoGateway.cadastrarOuAtualizar(any()))
                .thenReturn(produtoResponse);

        Produto produtoAtualizado = produtoAtualizarUseCaseImpl.atualizar(CODIGO, produtoRequest);

        Assertions.assertNotNull(produtoAtualizado);
    }

    @Test
    void naoDeveAtualizarUmProduto_Erro_ProdutoExistente() {
        when(produtoGateway.encontrarPorNome(anyString()))
                .thenReturn(Boolean.TRUE);

        Exception exception = Assertions.assertThrows(ProdutoDuplicatedException.class, () ->
                produtoAtualizarUseCaseImpl.atualizar(CODIGO, produtoRequest));

        String mensagemEsperada = "Produto informado já cadastrado";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}