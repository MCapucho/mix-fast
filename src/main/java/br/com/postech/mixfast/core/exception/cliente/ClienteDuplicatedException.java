package br.com.postech.mixfast.core.exception.cliente;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ClienteDuplicatedException extends RuntimeException {

    public ClienteDuplicatedException(String message) {
        super(message);
    }
}
