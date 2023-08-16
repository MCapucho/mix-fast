package br.com.postech.mixfast.core.entity.enums;

public enum StatusPagamento {

    AGUARDANDO("Aguardando"),
    APROVADO("Aprovado"),
    REPROVADO("REPROVADO");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
