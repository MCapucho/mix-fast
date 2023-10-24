package br.com.postech.mixfast.dataproviders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceFailedException extends RuntimeException {
    public ResourceFailedException(String message) {
        super(message);
    }
}
