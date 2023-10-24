package br.com.postech.mixfast.core.exception.categoria;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class CategoriaListEmptyException extends RuntimeException {

    public CategoriaListEmptyException(String message) {
        super(message);
    }
}
