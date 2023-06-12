package br.com.postech.mixfast.entrypoints.http.mapper;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.entrypoints.http.ProdutoHttp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ProdutoHttpMapper {

    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "preco", source = "preco")
    @Mapping(target = "categoria.codigo", source = "produto.categoria.codigo")
    ProdutoHttp entityToHttp(Produto produto);

    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "preco", source = "preco")
    @Mapping(target = "categoria.codigo", source = "produtoHttp.categoria.codigo")
    Produto httpToEntity(ProdutoHttp produtoHttp);
}
