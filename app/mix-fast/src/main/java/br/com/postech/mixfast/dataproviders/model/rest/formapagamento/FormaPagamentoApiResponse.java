package br.com.postech.mixfast.dataproviders.model.rest.formapagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoApiResponse {

    private String codigo;
    private String descricao;
}
