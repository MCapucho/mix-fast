package br.com.postech.mixfast.entrypoints.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String dateTime;
    private Integer code;
    private HttpStatus status;
    private List<String> errors;
}
