package br.com.postech.mixfast.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProduto {

    private Produto produto;
    private Integer quantidade;
    private String observacao;
}
