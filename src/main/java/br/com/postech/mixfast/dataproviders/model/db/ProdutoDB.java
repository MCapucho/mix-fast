package br.com.postech.mixfast.dataproviders.model.db;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_produtos")
public class ProdutoDB {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String codigo;

    @Column(name = "nome", length = 100, nullable = false, unique = true)
    @NotBlank(message = "O nome é um campo obrigatório, não pode ser nulo ou vazio")
    private String nome;

    @Column(name = "descricao", length = 200)
    private String descricao;

    @Column(name = "preco")
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "categoria_codigo", nullable = false)
    private CategoriaDB categoria;

    @OneToMany(mappedBy = "produto")
    private Set<PedidoProdutoDB> pedidosProdutos;
}
