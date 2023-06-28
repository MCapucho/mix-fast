package br.com.postech.mixfast.core.exception.produto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoDuplicatedException extends RuntimeException {

    public ProdutoDuplicatedException(String message) {
        super(message);
    }
}
