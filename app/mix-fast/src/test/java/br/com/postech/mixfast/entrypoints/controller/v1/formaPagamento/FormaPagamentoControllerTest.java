package br.com.postech.mixfast.entrypoints.controller.v1.formaPagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.*;
import br.com.postech.mixfast.entrypoints.handler.RestExceptionHandler;
import br.com.postech.mixfast.entrypoints.http.FormaPagamentoHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.FormaPagamentoHttpMapper;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class FormaPagamentoControllerTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private FormaPagamentoController formaPagamentoController;
    @Mock
    private FormaPagamentoHttpMapper formaPagamentoHttpMapper;
    @Mock
    private FormaPagamentoCadastrarUseCase formaPagamentoCadastrarUseCase;
    @Mock
    private FormaPagamentoBuscarTodasUseCase formaPagamentoBuscarTodasUseCase;
    @Mock
    private FormaPagamentoBuscarPorCodigoUseCase formaPagamentoBuscarPorCodigoUseCase;
    @Mock
    private FormaPagamentoAtualizarUseCase formaPagamentoAtualizarUseCase;
    @Mock
    private FormaPagamentoDeletarPorCodigoUseCase formaPagamentoDeletarPorCodigoUseCase;

    private JacksonTester<FormaPagamentoHttp> jacksonTester;
    private MockMvc mvc;
    private FormaPagamento formaPagamentoRequest;
    private FormaPagamento formaPagamentoResponse;
    private FormaPagamentoHttp formaPagamentoHttpRequest;
    private FormaPagamentoHttp formaPagamentoHttpResponse;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(formaPagamentoController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();

        formaPagamentoRequest = FormaPagamento.builder()
                .descricao("Forma Pagamento Teste")
                .build();

        formaPagamentoResponse = FormaPagamento.builder()
                .codigo(UUID.randomUUID().toString())
                .descricao("Forma Pagamento Teste")
                .build();

        formaPagamentoHttpRequest = FormaPagamentoHttp.builder()
                .descricao("Forma Pagamento Teste")
                .build();

        formaPagamentoHttpResponse = FormaPagamentoHttp.builder()
                .codigo(UUID.randomUUID().toString())
                .descricao("Forma Pagamento Teste")
                .build();
    }

    @SneakyThrows
    @Test
    void cadastrar() {
        when(formaPagamentoHttpMapper.httpToEntity(any(FormaPagamentoHttp.class)))
                .thenReturn(formaPagamentoRequest);

        when(formaPagamentoHttpMapper.entityToHttp(any(FormaPagamento.class)))
                .thenReturn(formaPagamentoHttpResponse);

        when(formaPagamentoCadastrarUseCase.cadastrar(any(FormaPagamento.class)))
                .thenReturn(formaPagamentoResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        post("/v1/formas_pagamento")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jacksonTester.write(formaPagamentoHttpRequest).getJson())
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.CREATED.value());
    }

    @SneakyThrows
    @Test
    void buscarTodas() {
        when(formaPagamentoBuscarTodasUseCase.buscarTodas())
                .thenReturn(List.of(formaPagamentoResponse));

        when(formaPagamentoHttpMapper.entityListToHttpList(anyList()))
                .thenReturn(List.of(formaPagamentoHttpResponse));

        MockHttpServletResponse response =
                mvc.perform(
                        get("/v1/formas_pagamento")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void buscarPorCodigo() {
        when(formaPagamentoHttpMapper.entityToHttp(any(FormaPagamento.class)))
                .thenReturn(formaPagamentoHttpResponse);

        when(formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(formaPagamentoResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        get("/v1/formas_pagamento/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void atualizar() {
        when(formaPagamentoHttpMapper.httpToEntity(any(FormaPagamentoHttp.class)))
                .thenReturn(formaPagamentoRequest);

        when(formaPagamentoHttpMapper.entityToHttp(any(FormaPagamento.class)))
                .thenReturn(formaPagamentoHttpResponse);

        when(formaPagamentoAtualizarUseCase.atualizar(anyString(), any(FormaPagamento.class)))
                .thenReturn(formaPagamentoResponse);

        MockHttpServletResponse response =
                mvc.perform(
                        put("/v1/formas_pagamento/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jacksonTester.write(formaPagamentoHttpRequest).getJson())
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @SneakyThrows
    @Test
    void deletarPorCodigo() {
        doNothing().when(formaPagamentoDeletarPorCodigoUseCase).deletarPorCodigo(anyString());

        MockHttpServletResponse response =
                mvc.perform(
                        delete("/v1/formas_pagamento/{codigo}", CODIGO)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
    }
}