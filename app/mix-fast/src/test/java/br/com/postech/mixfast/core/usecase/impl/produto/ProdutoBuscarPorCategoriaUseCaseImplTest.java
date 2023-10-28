package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.exception.produto.ProdutoListEmptyException;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaBuscarPorCodigoUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoBuscarPorCategoriaUseCaseImplTest {

    public static final String CATEGORIA = UUID.randomUUID().toString();

    @InjectMocks
    private ProdutoBuscarPorCategoriaUseCaseImpl produtoBuscarPorCategoriaUseCaseImpl;
    @Mock
    private ProdutoGateway produtoGateway;
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
    void deveBuscarPorCategoriaProdutoComSucesso() {
        when(categoriaBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(new Categoria());

        when(produtoGateway.buscarPorCategoria(anyString()))
                .thenReturn(List.of(produtoResponse));

        List<Produto> listaProdutos = produtoBuscarPorCategoriaUseCaseImpl.buscarPorCategoria(CATEGORIA);

        Assertions.assertNotNull(listaProdutos);
        Assertions.assertEquals(produtoRequest.getNome(), listaProdutos.get(0).getNome());
        Assertions.assertEquals(produtoRequest.getDescricao(), listaProdutos.get(0).getDescricao());
        Assertions.assertEquals(produtoRequest.getPreco(), listaProdutos.get(0).getPreco());
    }

    @Test
    void naoDeveBuscarPorCategoriaProduto_Erro() {
        Exception exception = Assertions.assertThrows(ProdutoListEmptyException.class, () ->
                produtoBuscarPorCategoriaUseCaseImpl.buscarPorCategoria(CATEGORIA));

        String mensagemEsperada = "Lista de produtos em branco";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}