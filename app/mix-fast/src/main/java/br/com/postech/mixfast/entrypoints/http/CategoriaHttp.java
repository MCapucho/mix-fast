package br.com.postech.mixfast.entrypoints.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaHttp {

    private String codigo;

    @NotBlank(message = "O nome é um campo obrigatório, não pode ser nulo ou vazio")
    private String nome;
}
