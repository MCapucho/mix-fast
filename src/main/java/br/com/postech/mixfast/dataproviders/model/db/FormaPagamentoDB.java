package br.com.postech.mixfast.dataproviders.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_formas_pagamento")
public class FormaPagamentoDB {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String codigo;

    @Column(name = "descricao", length = 50, nullable = false, unique = true)
    private String descricao;

    @OneToMany(mappedBy = "formaPagamento")
    private Set<PedidoDB> pedidos;
}
