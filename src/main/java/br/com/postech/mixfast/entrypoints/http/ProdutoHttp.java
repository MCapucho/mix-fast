package br.com.postech.mixfast.entrypoints.http;

import br.com.postech.mixfast.entrypoints.http.interfaces.PostValidation;
import br.com.postech.mixfast.entrypoints.http.interfaces.PutValidation;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoHttp {

    private String codigo;

    @NotBlank(message = "O nome é um campo obrigatório, não pode ser nulo ou vazio", groups = {PostValidation.class})
    private String nome;

    private String descricao;

    @PositiveOrZero(message = "O preço não pode ser do valor negativo", groups = {PostValidation.class, PutValidation.class})
    private BigDecimal preco;

    private CategoriaHttp categoria;
}
