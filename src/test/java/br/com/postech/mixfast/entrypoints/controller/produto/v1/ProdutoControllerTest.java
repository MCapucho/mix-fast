package br.com.postech.mixfast.entrypoints.controller.produto.v1;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.usecase.interfaces.produto.*;
import br.com.postech.mixfast.entrypoints.handler.RestExceptionHandler;
import br.com.postech.mixfast.entrypoints.http.CategoriaHttp;
import br.com.postech.mixfast.entrypoints.http.ProdutoHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.ProdutoHttpMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private ProdutoController produtoController;
    @Mock
    private ProdutoHttpMapper produtoHttpMapper;
    @Mock
    private ProdutoCadastrarUseCase produtoCadastrarUseCase;
    @Mock
    private ProdutoBuscarTodosUseCase produtoBuscarTodosUseCase;
    @Mock
    private ProdutoBuscarPorCodigoUseCase produtoBuscarPorCodigoUseCase;
    @Mock
    private ProdutoAtualizarUseCase produtoAtualizarUseCase;
    @Mock
    private ProdutoDeletarPorCodigoUseCase produtoDeletarPorCodigoUseCase;
    @Mock
    private ProdutoBuscarPorCategoriaUseCase produtoBuscarPorCategoriaUseCase;

    private JacksonTester<ProdutoHttp> jacksonTester;
    private MockMvc mvc;
    private Produto produtoRequest;
    private Produto produtoResponse;
    private ProdutoHttp produtoHttpRequest;
    private ProdutoHttp produtoHttpResponse;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(produtoController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();

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

        produtoHttpRequest = ProdutoHttp.builder()
                .nome("Produto Teste")
                .descricao("Produto Teste Descrição")
                .preco(new BigDecimal("0.00"))
                .categoria(CategoriaHttp.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .build();

        produtoHttpResponse = ProdutoHttp.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Produto Teste")
                .descricao("Produto Teste Descrição")
                .preco(new BigDecimal("0.00"))
                .categoria(CategoriaHttp.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .build();
    }

    @SneakyThrows
    @Test
    void cadastrar() {
        when(produtoHttpMapper.httpToEntity(any(ProdutoHttp.class)))
                .thenReturn(produtoRequest);

        when(produtoHttpMapper.entityToHttp(any(Produto.class)))
                .thenReturn(produtoHttpResponse);

        when(produtoCadastrarUseCase.cadastrar(any(Produto.class)))
                .thenReturn(produtoResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        post("/produtos/v1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jacksonTester.write(produtoHttpRequest).getJson())
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.CREATED.value());
    }

    @SneakyThrows
    @Test
    void buscarTodas() {
        when(produtoHttpMapper.entityToHttp(any(Produto.class)))
                .thenReturn(produtoHttpResponse);

        when(produtoBuscarTodosUseCase.buscarTodos())
                .thenReturn(List.of(produtoResponse));

        MockHttpServletResponse response =
                mvc.perform(
                        get("/produtos/v1")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void buscarPorCodigo() {
        when(produtoHttpMapper.entityToHttp(any(Produto.class)))
                .thenReturn(produtoHttpResponse);

        when(produtoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(produtoResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        get("/produtos/v1/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void atualizar() {
        when(produtoHttpMapper.httpToEntity(any(ProdutoHttp.class)))
                .thenReturn(produtoRequest);

        when(produtoHttpMapper.entityToHttp(any(Produto.class)))
                .thenReturn(produtoHttpResponse);

        when(produtoAtualizarUseCase.atualizar(anyString(), any(Produto.class)))
                .thenReturn(produtoResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        put("/produtos/v1/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jacksonTester.write(produtoHttpRequest).getJson())
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void deletarPorCodigo() {
        doNothing().when(produtoDeletarPorCodigoUseCase).deletarPorCodigo(anyString());

        MockHttpServletResponse response =
                mvc.perform(
                        delete("/produtos/v1/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
    }

    @SneakyThrows
    @Test
    void buscarPorCategoria() {
        when(produtoHttpMapper.entityToHttp(any(Produto.class)))
                .thenReturn(produtoHttpResponse);

        when(produtoBuscarPorCategoriaUseCase.buscarPorCategoria(anyString()))
                .thenReturn(List.of(produtoResponse));

        MockHttpServletResponse response =
                mvc.perform(
                        get("/produtos/v1/categoria/{categoria}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }
}