package br.com.postech.mixfast.dataproviders.model.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "tb_pedidos")
public class PedidoDB {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String codigo;

    @Column(name = "fila", nullable = false, unique = true)
    private Integer fila;

    @CreationTimestamp
    @Column(name = "data_pedido", nullable = false)
    private OffsetDateTime dataPedido;

    @ManyToOne
    @JoinColumn(name = "cliente_codigo")
    private ClienteDB cliente;

    @ToString.Exclude
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoProdutoDB> itens = new ArrayList<>();

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    public void calcularValorTotal() {
        getItens().forEach(PedidoProdutoDB::calcularValorTotal);

        this.valorTotal = getItens().stream()
                .map(PedidoProdutoDB::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
