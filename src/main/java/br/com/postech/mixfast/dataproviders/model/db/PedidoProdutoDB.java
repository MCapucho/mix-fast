package br.com.postech.mixfast.dataproviders.model.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_pedidos_produtos")
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

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column(name = "preco_total", nullable = false)
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
