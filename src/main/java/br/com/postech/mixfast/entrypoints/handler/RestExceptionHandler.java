package br.com.postech.mixfast.entrypoints.handler;

import br.com.postech.mixfast.core.exception.categoria.CategoriaBadRequestException;
import br.com.postech.mixfast.core.exception.cliente.ClienteBadRequestException;
import br.com.postech.mixfast.core.exception.cliente.ClienteDuplicatedException;
import br.com.postech.mixfast.core.exception.cliente.ClienteListEmptyException;
import br.com.postech.mixfast.core.exception.cliente.ClienteNotFoundException;
import br.com.postech.mixfast.core.exception.produto.ProdutoBadRequestException;
import br.com.postech.mixfast.core.exception.produto.ProdutoListEmptyException;
import br.com.postech.mixfast.core.exception.produto.ProdutoNotFoundException;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
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

    @ExceptionHandler(ClienteBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(ClienteBadRequestException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProdutoBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(ProdutoBadRequestException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClienteDuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ClienteDuplicatedException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ClienteNotFoundException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ProdutoNotFoundException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteListEmptyException.class)
    public ResponseEntity<ErrorResponse> handleListEmpty(ClienteListEmptyException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ProdutoListEmptyException.class)
    public ResponseEntity<ErrorResponse> handleListEmpty(ProdutoListEmptyException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ResourceFailedException.class)
    public ResponseEntity<ErrorResponse> handleFailed(ResourceFailedException ex) {
        return handleGeneric(null, ex.getMessage(), HttpStatus.CONFLICT);
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
