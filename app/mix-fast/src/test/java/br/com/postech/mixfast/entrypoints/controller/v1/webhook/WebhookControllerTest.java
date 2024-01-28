package br.com.postech.mixfast.entrypoints.controller.v1.webhook;

import br.com.postech.mixfast.core.entity.enums.StatusPagamento;
import br.com.postech.mixfast.core.usecase.interfaces.webhook.WebhookUseCase;
import br.com.postech.mixfast.entrypoints.handler.RestExceptionHandler;
import br.com.postech.mixfast.entrypoints.http.WebhookHttp;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class WebhookControllerTest {

    @InjectMocks
    private WebhookController webhookController;
    @Mock
    private WebhookUseCase webhookUseCase;

    private JacksonTester<WebhookHttp> jacksonTester;
    private MockMvc mvc;
    private WebhookHttp webhookHttp;


    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(webhookController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();

        webhookHttp = WebhookHttp.builder()
                .codigoPedido(UUID.randomUUID().toString())
                .statusPagamento(StatusPagamento.APROVADO.name())
                .build();
    }

    @SneakyThrows
    @Test
    void receberNotificacaoPagamento() {
        doNothing().when(webhookUseCase)
                .atualizar(anyString(), anyString());

        MockHttpServletResponse response =
                mvc.perform(
                        post("/v1/webhooks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jacksonTester.write(webhookHttp).getJson())
                ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }
}