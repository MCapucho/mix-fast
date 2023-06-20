package br.com.postech.mixfast.core.exception.pedido;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PedidoNotFoundException extends RuntimeException {

    public PedidoNotFoundException(String message) {
        super(message);
    }
}
