package br.com.postech.mixfast.core.exception.pedido;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PedidoFailedException extends RuntimeException {

    public PedidoFailedException(String message) {
        super(message);
    }
}
