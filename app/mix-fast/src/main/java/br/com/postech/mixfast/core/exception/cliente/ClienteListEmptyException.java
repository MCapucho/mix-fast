package br.com.postech.mixfast.core.exception.cliente;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ClienteListEmptyException extends RuntimeException {

    public ClienteListEmptyException(String message) {
        super(message);
    }
}
