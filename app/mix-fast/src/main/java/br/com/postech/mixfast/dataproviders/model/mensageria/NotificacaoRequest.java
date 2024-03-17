package br.com.postech.mixfast.dataproviders.model.mensageria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoRequest {

    private String codigoPedido;
    private String nomeCliente;
    private String emailCliente;
    private BigDecimal valorTotalPedido;
    private String statusPedido;
    private String statusPagamento;
}
