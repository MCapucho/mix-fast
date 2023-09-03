package br.com.postech.mixfast.core.entity;

import br.com.postech.mixfast.core.entity.enums.StatusPagamento;
import br.com.postech.mixfast.core.entity.enums.StatusPedido;
import br.com.postech.mixfast.core.exception.pedido.PedidoFailedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    private String codigo;
    private Cliente cliente;
    private List<PedidoProduto> itens;
    private FormaPagamento formaPagamento;
    private Integer fila;
    private BigDecimal valorTotal;
    private StatusPedido statusPedido = StatusPedido.RECEBIDO;
    private StatusPagamento statusPagamento = StatusPagamento.AGUARDANDO;
    private String qrCode;

    public void preparar() {
        setStatusPedido(StatusPedido.PREPARANDO);
    }

    public void entregar() {
        setStatusPedido(StatusPedido.ENTREGUE);
    }

    public void finalizar() {
        setStatusPedido(StatusPedido.FINALIZADO);
    }

    public void cancelar() {
        setStatusPedido(StatusPedido.CANCELADO);
    }

    private void setStatusPedido(StatusPedido novoStatus) {
        if (getStatusPedido().naoPodeAlterarPara(novoStatus)) {
            throw new PedidoFailedException(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            getCodigo(), getStatusPedido().getDescricao(),
                            novoStatus.getDescricao()));
        }

        this.statusPedido = novoStatus;
    }
}
