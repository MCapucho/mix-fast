package br.com.postech.mixfast.entrypoints.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookHttp {

    @JsonProperty(value = "codigo_pedido")
    private String codigoPedido;

    @JsonProperty(value = "status_pagamento")
    private String statusPagamento;
}
