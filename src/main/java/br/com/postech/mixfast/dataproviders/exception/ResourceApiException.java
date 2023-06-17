package br.com.postech.mixfast.dataproviders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceApiException extends RuntimeException {

    public ResourceApiException(String message) {
        super(message);
    }
}
