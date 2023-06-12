package br.com.postech.mixfast.dataproviders.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_pedido")
public class PedidoDB {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String codigo;

    @Column(name = "fila", unique = true)
    private Integer fila;

    @CreationTimestamp
    @Column(name = "data_pedido", nullable = false)
    private OffsetDateTime dataPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_codigo", nullable = false)
    private ClienteDB cliente;

    @ToString.Exclude
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoProdutoDB> itens;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    public void calcularValorTotal() {
        getItens().forEach(PedidoProdutoDB::calcularValorTotal);

        this.valorTotal = getItens().stream()
                .map(item -> item.getPrecoTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
