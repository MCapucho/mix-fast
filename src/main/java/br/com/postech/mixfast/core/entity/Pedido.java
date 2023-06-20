package br.com.postech.mixfast.core.entity;

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
    private Integer fila;
    private Cliente cliente;
    private BigDecimal valorTotal;
    private List<PedidoProduto> itens;
    private String qrCode;
    private StatusPedido status = StatusPedido.RECEBIDO;

    public void preparar() {
        setStatus(StatusPedido.PREPARANDO);
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
    }

    public void finalizar() {
        setStatus(StatusPedido.FINALIZADO);
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
    }

    private void setStatus(StatusPedido novoStatus) {
        if (getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new PedidoFailedException(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            getCodigo(), getStatus().getDescricao(),
                            novoStatus.getDescricao()));
        }

        this.status = novoStatus;
    }
}
