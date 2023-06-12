package br.com.postech.mixfast.dataproviders.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_pedido_produto")
public class PedidoProdutoDB {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "pedido_codigo")
    private PedidoDB pedido;

    @ManyToOne
    @JoinColumn(name = "produto_codigo")
    private ProdutoDB produto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    @Column(name = "preco_total")
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
