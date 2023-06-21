package br.com.postech.mixfast.entrypoints.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoHttp extends RepresentationModel<PedidoHttp> {

    private String codigo;
    private Integer fila;
    private ClienteHttp cliente;
    private BigDecimal valorTotal;
    @NotEmpty(message = "A lista de produto está vazia")
    private List<PedidoProdutoHttp> itens;
    private String qrCode;
    private String status;
}
