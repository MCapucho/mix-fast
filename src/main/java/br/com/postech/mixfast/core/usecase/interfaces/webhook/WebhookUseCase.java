package br.com.postech.mixfast.core.usecase.interfaces.webhook;

public interface WebhookUseCase {

    void atualizar(String codigoPedido, String statusPagamento);
}
