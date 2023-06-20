package br.com.postech.mixfast.entrypoints.http.mapper;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.entity.PedidoProduto;
import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.entrypoints.http.PedidoHttp;
import br.com.postech.mixfast.entrypoints.http.PedidoProdutoHttp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel="spring", imports = Collection.class)
public interface PedidoHttpMapper {

    @Mapping(target = "itens", source = "pedido.itens", qualifiedByName = "convertEntityToHttp")
    PedidoHttp entityToHttp(Pedido pedido);

    @Mapping(target = "itens", source = "pedidoHttp.itens", qualifiedByName = "convertHttpToEntity")
    @Mapping(target = "status", defaultValue = "RECEBIDO")
    Pedido httpToEntity(PedidoHttp pedidoHttp);

    @Named("convertHttpToEntity")
    default List<PedidoProduto> convertHttpToEntity(List<PedidoProdutoHttp> listaPedidoProdutosHttp){
        List<PedidoProduto> listaPedidoProdutos = new ArrayList<>();
        listaPedidoProdutosHttp.forEach(element -> {
            Produto produto = Produto.builder()
                    .codigo(element.getProduto())
                    .build();

            PedidoProduto pedidoProduto = PedidoProduto.builder()
                    .produto(produto)
                    .quantidade(element.getQuantidade())
                    .observacao(element.getObservacao())
                    .build();

            listaPedidoProdutos.add(pedidoProduto);
        });
        return listaPedidoProdutos;
    }

    @Named("convertEntityToHttp")
    default List<PedidoProdutoHttp> convertEntityToHttp(List<PedidoProduto> listaPedidoProdutos){
        List<PedidoProdutoHttp> listaPedidoProdutosHttp = new ArrayList<>();
        listaPedidoProdutos.forEach(element -> {
            PedidoProdutoHttp pedidoProdutoHttp = PedidoProdutoHttp.builder()
                    .produto(element.getProduto().getCodigo())
                    .quantidade(element.getQuantidade())
                    .observacao(element.getObservacao())
                    .build();

            listaPedidoProdutosHttp.add(pedidoProdutoHttp);
        });
        return listaPedidoProdutosHttp;
    }
}
