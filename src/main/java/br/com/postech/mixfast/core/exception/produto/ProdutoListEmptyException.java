package br.com.postech.mixfast.core.exception.produto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ProdutoListEmptyException extends RuntimeException {

    public ProdutoListEmptyException(String message) {
        super(message);
    }
}
