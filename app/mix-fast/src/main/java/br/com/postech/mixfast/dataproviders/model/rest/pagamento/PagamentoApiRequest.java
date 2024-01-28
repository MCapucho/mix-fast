package br.com.postech.mixfast.dataproviders.model.rest.pagamento;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoApiRequest {

    @JsonProperty("pedido")
    private String pedido;

    @JsonProperty("valor_total")
    private BigDecimal valorTotal;
}
