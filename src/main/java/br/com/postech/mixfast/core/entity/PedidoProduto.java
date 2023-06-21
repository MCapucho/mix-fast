package br.com.postech.mixfast.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProduto {

    private Produto produto;
    private Integer quantidade;
    private String observacao;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;

    public void calcularValorTotal() {
        BigDecimal valorUnitario = this.precoUnitario;
        Integer quantidadeProduto = this.getQuantidade();

        if (valorUnitario == null) {
            valorUnitario = BigDecimal.ZERO;
        }

        if (quantidadeProduto == null) {
            quantidadeProduto = 0;
        }

        this.setPrecoTotal(valorUnitario.multiply(new BigDecimal(quantidadeProduto)));
    }
}
