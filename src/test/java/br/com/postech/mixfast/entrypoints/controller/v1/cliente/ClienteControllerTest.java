package br.com.postech.mixfast.entrypoints.controller.v1.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.*;
import br.com.postech.mixfast.entrypoints.handler.RestExceptionHandler;
import br.com.postech.mixfast.entrypoints.http.ClienteHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.ClienteHttpMapper;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    public static final String CODIGO = UUID.randomUUID().toString();
    public static final String CPF = "26707200054";

    @InjectMocks
    private ClienteController clienteController;
    @Mock
    private ClienteHttpMapper clienteHttpMapper;
    @Mock
    private ClienteCadastrarUseCase clienteCadastrarUseCase;
    @Mock
    private ClienteBuscarTodosUseCase clienteBuscarTodosUseCase;
    @Mock
    private ClienteBuscarPorCodigoUseCase clienteBuscarPorCodigoUseCase;
    @Mock
    private ClienteAtualizarUseCase clienteAtualizarUseCase;
    @Mock
    private ClienteDeletarPorCodigoUseCase clienteDeletarPorCodigoUseCase;
    @Mock
    private ClienteBuscarPorCpfUseCase clienteBuscarPorCpfUseCase;

    private JacksonTester<ClienteHttp> jacksonTester;
    private MockMvc mvc;
    private Cliente clienteRequest;
    private Cliente clienteResponse;
    private ClienteHttp clienteHttpRequest;
    private ClienteHttp clienteHttpResponse;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();

        clienteRequest = Cliente.builder()
                .nome("Cliente Teste")
                .cpf("26707200054")
                .email("teste@mixfast.com.br")
                .build();

        clienteResponse = Cliente.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Cliente Teste")
                .cpf("26707200054")
                .email("teste@mixfast.com.br")
                .build();

        clienteHttpRequest = ClienteHttp.builder()
                .nome("Cliente Teste")
                .cpf("26707200054")
                .email("teste@mixfast.com.br")
                .build();

        clienteHttpResponse = ClienteHttp.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Cliente Teste")
                .cpf("26707200054")
                .email("teste@mixfast.com.br")
                .build();
    }

    @SneakyThrows
    @Test
    void cadastrar() {
        when(clienteHttpMapper.httpToEntity(any(ClienteHttp.class)))
                .thenReturn(clienteRequest);

        when(clienteHttpMapper.entityToHttp(any(Cliente.class)))
                .thenReturn(clienteHttpResponse);

        when(clienteCadastrarUseCase.cadastrar(any(Cliente.class)))
                .thenReturn(clienteResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        post("/v1/clientes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jacksonTester.write(clienteHttpRequest).getJson())
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.CREATED.value());
    }

    @SneakyThrows
    @Test
    void buscarTodas() {
        when(clienteHttpMapper.entityToHttp(any(Cliente.class)))
                .thenReturn(clienteHttpResponse);

        when(clienteBuscarTodosUseCase.buscarTodos())
                .thenReturn(List.of(clienteResponse));

        MockHttpServletResponse response =
                mvc.perform(
                        get("/v1/clientes")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void buscarPorCodigo() {
        when(clienteHttpMapper.entityToHttp(any(Cliente.class)))
                .thenReturn(clienteHttpResponse);

        when(clienteBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(clienteResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        get("/v1/clientes/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void atualizar() {
        when(clienteHttpMapper.httpToEntity(any(ClienteHttp.class)))
                .thenReturn(clienteRequest);

        when(clienteHttpMapper.entityToHttp(any(Cliente.class)))
                .thenReturn(clienteHttpResponse);

        when(clienteAtualizarUseCase.atualizar(anyString(), any(Cliente.class)))
                .thenReturn(clienteResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        put("/v1/clientes/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jacksonTester.write(clienteHttpRequest).getJson())
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void deletarPorCodigo() {
        doNothing().when(clienteDeletarPorCodigoUseCase).deletarPorCodigo(anyString());

        MockHttpServletResponse response =
                mvc.perform(
                        delete("/v1/clientes/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
    }

    @SneakyThrows
    @Test
    void buscarPorCpf() {
        when(clienteHttpMapper.entityToHttp(any(Cliente.class)))
                .thenReturn(clienteHttpResponse);

        when(clienteBuscarPorCpfUseCase.buscarPorCpf(anyString()))
                .thenReturn(clienteResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        get("/v1/clientes/cpf/{cpf}", CPF)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }
}