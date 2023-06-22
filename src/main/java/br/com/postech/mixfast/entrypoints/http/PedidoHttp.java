package br.com.postech.mixfast.entrypoints.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoHttp extends RepresentationModel<PedidoHttp> {

    private String codigo;

    private ClienteHttp cliente;

    @NotEmpty(message = "A lista de produto está vazia")
    private List<PedidoProdutoHttp> itens;

    @JsonProperty(value = "forma_pagamento")
    private FormaPagamentoHttp formaPagamento;

    private Integer fila;

    @JsonProperty(value = "valor_total")
    private BigDecimal valorTotal;

    private String status;

    @JsonProperty(value = "qr_code")
    private String qrCode;
}
