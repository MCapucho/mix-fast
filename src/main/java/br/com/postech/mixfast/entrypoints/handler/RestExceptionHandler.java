package br.com.postech.mixfast.entrypoints.handler;

import br.com.postech.mixfast.core.exception.categoria.CategoriaBadRequestException;
import br.com.postech.mixfast.core.exception.categoria.CategoriaListEmptyException;
import br.com.postech.mixfast.core.exception.categoria.CategoriaNotFoundException;
import br.com.postech.mixfast.core.exception.cliente.ClienteBadRequestException;
import br.com.postech.mixfast.core.exception.cliente.ClienteDuplicatedException;
import br.com.postech.mixfast.core.exception.cliente.ClienteListEmptyException;
import br.com.postech.mixfast.core.exception.cliente.ClienteNotFoundException;
import br.com.postech.mixfast.core.exception.formaPagamento.FormaPagamentoBadRequestException;
import br.com.postech.mixfast.core.exception.formaPagamento.FormaPagamentoDuplicatedException;
import br.com.postech.mixfast.core.exception.formaPagamento.FormaPagamentoNotFoundException;
import br.com.postech.mixfast.core.exception.pedido.PedidoFailedException;
import br.com.postech.mixfast.core.exception.pedido.PedidoListEmptyException;
import br.com.postech.mixfast.core.exception.pedido.PedidoNotFoundException;
import br.com.postech.mixfast.core.exception.pedido.PedidoStatusException;
import br.com.postech.mixfast.core.exception.produto.ProdutoBadRequestException;
import br.com.postech.mixfast.core.exception.produto.ProdutoListEmptyException;
import br.com.postech.mixfast.core.exception.produto.ProdutoNotFoundException;
import br.com.postech.mixfast.dataproviders.exception.ResourceApiException;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
import feign.RetryableException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @ExceptionHandler(CategoriaBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(CategoriaBadRequestException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoriaListEmptyException.class)
    public ResponseEntity<ErrorResponse> handleListEmpty(CategoriaListEmptyException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(CategoriaNotFoundException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(ClienteBadRequestException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClienteDuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleDuplicated(ClienteDuplicatedException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClienteListEmptyException.class)
    public ResponseEntity<ErrorResponse> handleListEmpty(ClienteListEmptyException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ClienteNotFoundException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FormaPagamentoBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(FormaPagamentoBadRequestException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FormaPagamentoDuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleDuplicated(FormaPagamentoDuplicatedException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FormaPagamentoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(FormaPagamentoNotFoundException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PedidoFailedException.class)
    public ResponseEntity<ErrorResponse> handleFailed(PedidoFailedException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PedidoListEmptyException.class)
    public ResponseEntity<ErrorResponse> handleListEmpty(PedidoListEmptyException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(PedidoNotFoundException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PedidoStatusException.class)
    public ResponseEntity<ErrorResponse> handleStatus(PedidoStatusException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProdutoBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(ProdutoBadRequestException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProdutoListEmptyException.class)
    public ResponseEntity<ErrorResponse> handleListEmpty(ProdutoListEmptyException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ProdutoNotFoundException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceApiException.class)
    public ResponseEntity<ErrorResponse> handleClient(ResourceApiException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceFailedException.class)
    public ResponseEntity<ErrorResponse> handleFailed(ResourceFailedException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ErrorResponse> handleClient(RetryableException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValid(MethodArgumentNotValidException ex) {
        return handleGeneric(getErrors(ex), null, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> handleGeneric(List<String> errors, String error, HttpStatus httpStatus) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .dateTime(LocalDateTime.now().format(formatter))
                .status(httpStatus)
                .code(httpStatus.value())
                .errors(error != null ? List.of(error) : errors)
                .build();

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private List<String> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
