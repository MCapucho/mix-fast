package br.com.postech.mixfast.core.usecase.interfaces.pedido;

public interface PedidoAtualizarStatusUseCase {

    void preparar(String codigo);
    void entregar(String codigo);
    void finalizar(String codigo);
    void cancelar(String codigo);
}
