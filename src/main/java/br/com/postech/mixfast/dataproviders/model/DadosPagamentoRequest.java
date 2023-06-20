package br.com.postech.mixfast.dataproviders.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosPagamentoRequest {

    @JsonProperty("external_reference")
    private String referenciaExterna;

    @JsonProperty("description")
    private String descricao;

    @JsonProperty("title")
    private String titulo;

    @JsonProperty("total_amount")
    private BigDecimal valorTotal;

    @JsonProperty("items")
    private List<DadosPagamentoItensRequest> itens;

    @JsonProperty("sponsor")
    private DadosPagamentoResponsavelRequest responsavel;

    @Data
    @Builder
    public static class DadosPagamentoItensRequest {

        @JsonProperty("sku_number")
        private String codigo;

        @JsonProperty("category")
        private String categoria;

        @JsonProperty("title")
        private String titulo;

        @JsonProperty("description")
        private String descricao;

        @JsonProperty("unit_price")
        private BigDecimal precoUnitario;

        @JsonProperty("quantity")
        private Integer quantidade;

        @JsonProperty("unit_measure")
        private String unidade;

        @JsonProperty("total_amount")
        private BigDecimal valorTotal;
    }

    @Data
    @Builder
    public static class DadosPagamentoResponsavelRequest {

        @JsonProperty("id")
        private Integer codigo;
    }
}
