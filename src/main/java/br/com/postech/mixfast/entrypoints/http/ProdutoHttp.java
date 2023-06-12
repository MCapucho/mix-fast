package br.com.postech.mixfast.entrypoints.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "O nome é um campo obrigatório, não pode ser nulo ou vazio")
    private String nome;

    private String descricao;

    private BigDecimal preco;

    private CategoriaHttp categoria;


    private Integer quantidade;
}
