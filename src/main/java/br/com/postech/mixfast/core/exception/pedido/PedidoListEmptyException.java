package br.com.postech.mixfast.core.exception.pedido;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class PedidoListEmptyException extends RuntimeException {

    public PedidoListEmptyException(String message) {
        super(message);
    }
}
