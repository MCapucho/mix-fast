package br.com.postech.mixfast.core.entity.enums;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

	RECEBIDO("Recebido"),
	PREPARANDO("Em Preparação", RECEBIDO),
	ENTREGUE("Pronto", PREPARANDO),
	FINALIZADO("Finalizado", ENTREGUE),
	CANCELADO("Cancelado", RECEBIDO);

	private final String descricao;

	private final List<StatusPedido> statusAnteriores;

	StatusPedido(String descricao, StatusPedido... statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}

	public String getDescricao() {
		return this.descricao;
	}

	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
	}
}