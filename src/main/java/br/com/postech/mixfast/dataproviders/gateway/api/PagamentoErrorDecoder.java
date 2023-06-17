package br.com.postech.mixfast.dataproviders.gateway.api;

import br.com.postech.mixfast.dataproviders.exception.ResourceApiException;
import br.com.postech.mixfast.dataproviders.model.MensagemException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class PagamentoErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        MensagemException message;

        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, MensagemException.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        if (HttpStatus.valueOf(response.status()).is5xxServerError()) {
            return new RetryableException(response.status(),
                    "Erro na comunicação com o servidor",
                    response.request().httpMethod(),
                    new Date(),
                    response.request());
        } else if (HttpStatus.valueOf(response.status()).is4xxClientError()) {
            return new ResourceApiException(message.getMessage() != null ?
                    message.getMessage() :
                    "Erro nos dados informados");
        } else {
            return errorDecoder.decode(methodKey, response);
        }
    }
}