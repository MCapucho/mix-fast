package br.com.postech.mixfast.dataproviders.model.db;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_clientes")
public class ClienteDB {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String codigo;

    @Column(name = "nome", length = 100, nullable = false)
    @NotBlank(message = "O nome é um campo obrigatório, não pode ser nulo ou vazio")
    private String nome;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    @NotBlank(message = "O cpf é um campo obrigatório, não pode ser nulo ou vazio")
    private String cpf;

    @Column(name = "email", length = 200, nullable = false, unique = true)
    @NotBlank(message = "O email é um campo obrigatório, não pode ser nulo ou vazio")
    private String email;

    @OneToMany(mappedBy = "cliente")
    private Set<PedidoDB> pedidos;
}
