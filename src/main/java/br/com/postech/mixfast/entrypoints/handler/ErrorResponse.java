package br.com.postech.mixfast.entrypoints.handler;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "dd/MM/yyyy hh:MM:ss")
    private String dateTime;
    @Schema(example = "000")
    private Integer code;
    @Schema(example = "OK")
    private HttpStatus status;
    @Schema(example = "[\"Mensagem do Erro\"]")
    private List<String> errors;
}
