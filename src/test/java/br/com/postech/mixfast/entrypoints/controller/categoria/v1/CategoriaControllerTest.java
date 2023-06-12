package br.com.postech.mixfast.entrypoints.controller.categoria.v1;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.*;
import br.com.postech.mixfast.entrypoints.handler.RestExceptionHandler;
import br.com.postech.mixfast.entrypoints.http.CategoriaHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.CategoriaHttpMapper;
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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class CategoriaControllerTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private CategoriaController categoriaController;
    @Mock
    private CategoriaHttpMapper categoriaHttpMapper;
    @Mock
    private CategoriaCadastrarUseCase categoriaCadastrarUseCase;
    @Mock
    private CategoriaBuscarTodasUseCase categoriaBuscarTodasUseCase;
    @Mock
    private CategoriaBuscarPorCodigoUseCase categoriaBuscarPorCodigoUseCase;
    @Mock
    private CategoriaAtualizarUseCase categoriaAtualizarUseCase;
    @Mock
    private CategoriaDeletarPorCodigoUseCase categoriaDeletarPorCodigoUseCase;

    private JacksonTester<CategoriaHttp> jacksonTester;
    private MockMvc mvc;
    private Categoria categoriaRequest;
    private Categoria categoriaResponse;
    private CategoriaHttp categoriaHttpRequest;
    private CategoriaHttp categoriaHttpResponse;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(categoriaController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();

        categoriaRequest = Categoria.builder()
                .nome("Categoria Teste")
                .build();

        categoriaResponse = Categoria.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Categoria Teste")
                .build();

        categoriaHttpRequest = CategoriaHttp.builder()
                .nome("Categoria Teste")
                .build();

        categoriaHttpResponse = CategoriaHttp.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Categoria Teste")
                .build();
    }

    @SneakyThrows
    @Test
    void cadastrar() {
        when(categoriaHttpMapper.httpToEntity(any(CategoriaHttp.class)))
                .thenReturn(categoriaRequest);

        when(categoriaHttpMapper.entityToHttp(any(Categoria.class)))
                .thenReturn(categoriaHttpResponse);

        when(categoriaCadastrarUseCase.cadastrar(any(Categoria.class)))
                .thenReturn(categoriaResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        post("/categorias/v1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jacksonTester.write(categoriaHttpRequest).getJson())
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.CREATED.value());
    }

    @SneakyThrows
    @Test
    void buscarTodas() {
        when(categoriaHttpMapper.entityToHttp(any(Categoria.class)))
                .thenReturn(categoriaHttpResponse);

        when(categoriaBuscarTodasUseCase.buscarTodas())
                .thenReturn(List.of(categoriaResponse));

        MockHttpServletResponse response =
                mvc.perform(
                        get("/categorias/v1")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void buscarPorCodigo() {
        when(categoriaHttpMapper.entityToHttp(any(Categoria.class)))
                .thenReturn(categoriaHttpResponse);

        when(categoriaBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(categoriaResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        get("/categorias/v1/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void atualizar() {
        when(categoriaHttpMapper.httpToEntity(any(CategoriaHttp.class)))
                .thenReturn(categoriaRequest);

        when(categoriaHttpMapper.entityToHttp(any(Categoria.class)))
                .thenReturn(categoriaHttpResponse);

        when(categoriaAtualizarUseCase.atualizar(anyString(), any(Categoria.class)))
                .thenReturn(categoriaResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        put("/categorias/v1/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jacksonTester.write(categoriaHttpRequest).getJson())
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void deletarPorCodigo() {
        doNothing().when(categoriaDeletarPorCodigoUseCase).deletarPorCodigo(anyString());

        MockHttpServletResponse response =
                mvc.perform(
                        delete("/categorias/v1/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
    }
}