package br.com.postech.mixfast.dataproviders.gateway.api.formapagamento;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Response;
import feign.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class FormaPagamentoErrorDecoderTest {

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void testDecodeError5xx_NoBody() {
        String response5xx = "";
        Response response = criarResponse(500, response5xx);

        Exception exception = new FormaPagamentoErrorDecoder().decode("method", response);

        assertNotNull(exception);
        assertEquals("Exception", exception.getClass().getSimpleName());
    }

    @Test
    void testDecodeError5xx() {
        String response5xx = "{\"timestamp\": \"1234\",\"status\": 500,\"error\": \"error\", \"message\": \"Teste\", \"path\": \"path\", \"causes\": [\"Teste\"]}";
        Response response = criarResponse(500, response5xx);

        Exception exception = new FormaPagamentoErrorDecoder().decode("method", response);

        assertNotNull(exception);
        assertEquals("RetryableException", exception.getClass().getSimpleName());
        assertEquals("Erro na comunicação com o servidor", exception.getMessage());
    }

    @Test
    void testDecodeError4xx() {
        String response4xx = "{\"timestamp\": \"1234\",\"status\": 400,\"error\": \"error\", \"message\": \"Erro nos dados informados\", \"path\": \"path\", \"causes\": [\"Teste\"]}";
        Response response = criarResponse(400, response4xx);

        Exception exception = new FormaPagamentoErrorDecoder().decode("method", response);

        assertNotNull(exception);
        assertEquals("ResourceApiException", exception.getClass().getSimpleName());
        assertEquals("Erro nos dados informados", exception.getMessage());
    }

    @Test
    void testDecodeError4xx_BlankMessage() {
        String response4xx = "{\"timestamp\": \"1234\",\"status\": 400,\"error\": \"error\", \"message\": null, \"path\": \"path\", \"causes\": [\"Teste\"]}";
        Response response = criarResponse(401, response4xx);

        Exception exception = new FormaPagamentoErrorDecoder().decode("method", response);

        assertNotNull(exception);
        assertEquals("ResourceApiException", exception.getClass().getSimpleName());
        assertEquals("Erro nos dados informados", exception.getMessage());
    }

    @Test
    void testDecodeError3xx() {
        String response3xx = "{\"timestamp\": \"1234\",\"status\": 300,\"error\": \"error\", \"message\": \"Teste\", \"path\": \"path\", \"causes\": [\"Teste\"]}";
        Response response = criarResponse(300, response3xx);

        Exception exception = new FormaPagamentoErrorDecoder().decode("method", response);

        assertNotNull(exception);
        assertEquals("FeignException", exception.getClass().getSimpleName());
    }

    private Response criarResponse(int status, String body) {
        return Response.builder()
                .status(status)
                .headers(Collections.emptyMap())
                .request(Request.create(Request.HttpMethod.GET, "/api", Collections.emptyMap(), null, Util.UTF_8))
                .body(body, Util.UTF_8)
                .build();
    }
}