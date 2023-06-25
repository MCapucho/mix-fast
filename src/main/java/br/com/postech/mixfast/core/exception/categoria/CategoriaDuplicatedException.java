package br.com.postech.mixfast.core.exception.categoria;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoriaDuplicatedException extends RuntimeException {

    public CategoriaDuplicatedException(String message) {
        super(message);
    }
}
