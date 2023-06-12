package br.com.postech.mixfast.entrypoints.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteHttp {

    private String codigo;

    @NotBlank(message = "O nome é um campo obrigatório, não pode ser nulo ou vazio")
    private String nome;

    @CPF(message = "CPF informado inválido")
    @NotBlank(message = "O cpf é um campo obrigatório, não pode ser nulo ou vazio")
    private String cpf;

    @Email(message = "E-mail informado inválido")
    @NotBlank(message = "O e-mail é um campo obrigatório, não pode ser nulo ou vazio")
    private String email;
}
