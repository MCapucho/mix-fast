package br.com.postech.mixfast.dataproviders.gateway.database;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
import br.com.postech.mixfast.dataproviders.model.db.CategoriaDB;
import br.com.postech.mixfast.dataproviders.model.db.ProdutoDB;
import br.com.postech.mixfast.dataproviders.model.mapper.ProdutoDBMapper;
import br.com.postech.mixfast.dataproviders.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoGatewayImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();
    public static final String NOME_PRODUTO = "Produto Teste";

    @InjectMocks
    private ProdutoGatewayImpl produtoGatewayImpl;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private ProdutoDBMapper produtoDBMapper;

    private Produto produtoRequest;
    private Produto produtoResponse;
    private ProdutoDB produtoDBRequest;
    private ProdutoDB produtoDBResponse;

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

        produtoDBRequest = ProdutoDB.builder()
                .nome("Produto Teste")
                .descricao("Produto Teste Descrição")
                .preco(new BigDecimal("0.00"))
                .categoria(CategoriaDB.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .build();

        produtoDBResponse = ProdutoDB.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Produto Teste")
                .descricao("Produto Teste Descrição")
                .preco(new BigDecimal("0.00"))
                .categoria(CategoriaDB.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .build();
    }

    @Test
    void cadastrarOuAtualizar_Sucesso() {
        when(produtoDBMapper.entityToDB(any(Produto.class)))
                .thenReturn(produtoDBRequest);

        when(produtoRepository.save(any(ProdutoDB.class)))
                .thenReturn(produtoDBResponse);

        when(produtoDBMapper.dbToEntity(any(ProdutoDB.class)))
                .thenReturn(produtoResponse);

        Produto produtoCadastrado = produtoGatewayImpl.cadastrarOuAtualizar(produtoRequest);

        Assertions.assertNotNull(produtoCadastrado);
        Assertions.assertEquals(produtoRequest.getNome(), produtoCadastrado.getNome());
    }

    @Test
    void cadastrarOuAtualizar_Erro() {
        when(produtoDBMapper.entityToDB(any(Produto.class)))
                .thenReturn(produtoDBResponse);

        when(produtoRepository.save(any(ProdutoDB.class)))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                produtoGatewayImpl.cadastrarOuAtualizar(produtoRequest));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarTodos_Sucesso() {
        when(produtoRepository.findAll())
                .thenReturn(List.of(produtoDBResponse));

        when(produtoDBMapper.dbToEntity(any(ProdutoDB.class)))
                .thenReturn(produtoResponse);

        List<Produto> listaProdutoEncontrado = produtoGatewayImpl.buscarTodos();

        Assertions.assertNotNull(listaProdutoEncontrado);
        Assertions.assertEquals(1, listaProdutoEncontrado.size());
    }

    @Test
    void buscarTodos_Erro() {
        when(produtoRepository.findAll())
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                produtoGatewayImpl.buscarTodos());

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarPorCodigo_Sucesso() {
        when(produtoRepository.findById(anyString()))
                .thenReturn(Optional.of(produtoDBResponse));

        when(produtoDBMapper.dbToEntity(any(ProdutoDB.class)))
                .thenReturn(produtoResponse);

        Produto produtoEncontrado = produtoGatewayImpl.buscarPorCodigo(CODIGO);

        Assertions.assertNotNull(produtoEncontrado);
        Assertions.assertEquals(produtoRequest.getNome(), produtoEncontrado.getNome());
    }

    @Test
    void buscarPorCodigo_Erro() {
        when(produtoRepository.findById(anyString()))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                produtoGatewayImpl.buscarPorCodigo(CODIGO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void deletarPorCodigo_Sucesso() {
        doNothing().when(produtoRepository)
                .deleteById(CODIGO);

        produtoGatewayImpl.deletarPorCodigo(CODIGO);

        verify(produtoRepository, times(1)).deleteById(CODIGO);
    }

    @Test
    void deletarPorCodigo_Erro() {
        doThrow(new ArithmeticException()).when(produtoRepository)
                .deleteById(anyString());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                produtoGatewayImpl.deletarPorCodigo(CODIGO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarPorCategoria_Sucesso() {
        when(produtoRepository.findByCategoriaCodigo(anyString()))
                .thenReturn(List.of(produtoDBResponse));

        when(produtoDBMapper.dbToEntity(any(ProdutoDB.class)))
                .thenReturn(produtoResponse);

        List<Produto> listaProdutoEncontrado = produtoGatewayImpl.buscarPorCategoria(CODIGO);

        Assertions.assertNotNull(listaProdutoEncontrado);
        Assertions.assertEquals(1, listaProdutoEncontrado.size());
    }

    @Test
    void buscarPorCategoria_Erro() {
        when(produtoRepository.findByCategoriaCodigo(anyString()))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                produtoGatewayImpl.buscarPorCategoria(CODIGO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void encontrarPorNome_Sucesso() {
        when(produtoRepository.existsByNome(anyString()))
                .thenReturn(Boolean.TRUE);

        Boolean produtoCPFExistente = produtoGatewayImpl.encontrarPorNome(NOME_PRODUTO);

        Assertions.assertTrue(produtoCPFExistente);
    }

    @Test
    void encontrarPorNome_Erro() {
        when(produtoRepository.existsByNome(anyString()))
                .thenThrow(new NullPointerException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                produtoGatewayImpl.encontrarPorNome(NOME_PRODUTO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}