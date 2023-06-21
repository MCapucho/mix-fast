package br.com.postech.mixfast.dataproviders.model.db;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "observacao", length = 150)
    private String observacao;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column(name = "preco_total", nullable = false)
    private BigDecimal precoTotal;
}
