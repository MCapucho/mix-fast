package br.com.postech.mixfast.core.exception.formaPagamento;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class FormaPagamentoListEmptyException extends RuntimeException {

    public FormaPagamentoListEmptyException(String message) {
        super(message);
    }
}
