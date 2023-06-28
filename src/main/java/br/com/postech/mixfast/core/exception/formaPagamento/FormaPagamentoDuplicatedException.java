package br.com.postech.mixfast.core.exception.formaPagamento;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormaPagamentoDuplicatedException extends RuntimeException {

    public FormaPagamentoDuplicatedException(String message) {
        super(message);
    }
}
