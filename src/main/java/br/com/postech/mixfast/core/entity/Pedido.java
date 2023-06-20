package br.com.postech.mixfast.core.entity;

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
    private String status;
}
