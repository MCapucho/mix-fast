package br.com.postech.mixfast.core.exception.produto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoBadRequestException extends RuntimeException {

    public ProdutoBadRequestException(String message) {
        super(message);
    }
}
