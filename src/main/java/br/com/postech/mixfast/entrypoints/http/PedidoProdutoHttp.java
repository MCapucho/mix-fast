package br.com.postech.mixfast.entrypoints.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoProdutoHttp {

    @NotBlank(message = "O produto é um campo obrigatório, não pode ser nulo ou vazio")
    private String produto;

    @Min(1)
    @NotNull
    private Integer quantidade;

    private String observacao;
}
