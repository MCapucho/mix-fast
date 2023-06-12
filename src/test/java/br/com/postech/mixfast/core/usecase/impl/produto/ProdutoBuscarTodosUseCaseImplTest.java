package br.com.postech.mixfast.core.usecase.impl.produto;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.exception.produto.ProdutoListEmptyException;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoBuscarTodosUseCaseImplTest {

    @InjectMocks
    private ProdutoBuscarTodosUseCaseImpl produtoBuscarTodosUseCaseImpl;
    @Mock
    private ProdutoGateway produtoGateway;

    private Produto produtoRequest;
    private Produto produtoResponse;

    @BeforeEach
    void setUp() {
        produtoRequest = Produto.builder()
                .nome("Produto Teste")
                .descricao("Produto Teste Descrição")
                .preco(new BigDecimal(0.00))
                .categoria(Categoria.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .build();

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
    void deveBuscarTodosProdutoComSucesso() {
        when(produtoGateway.buscarTodos())
                .thenReturn(List.of(produtoResponse));

        List<Produto> listaProdutos = produtoBuscarTodosUseCaseImpl.buscarTodos();

        Assertions.assertNotNull(listaProdutos);
        Assertions.assertEquals(produtoRequest.getNome(), listaProdutos.get(0).getNome());
        Assertions.assertEquals(produtoRequest.getDescricao(), listaProdutos.get(0).getDescricao());
        Assertions.assertEquals(produtoRequest.getPreco(), listaProdutos.get(0).getPreco());
    }

    @Test
    void naoDeveBuscarTodosProduto_Erro() {
        Exception exception = Assertions.assertThrows(ProdutoListEmptyException.class, () -> {
            produtoBuscarTodosUseCaseImpl.buscarTodos();
        });

        String mensagemEsperada = "Lista de produtos em branco";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}