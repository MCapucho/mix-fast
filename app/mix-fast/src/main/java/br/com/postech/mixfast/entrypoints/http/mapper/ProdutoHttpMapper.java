package br.com.postech.mixfast.entrypoints.http.mapper;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.entrypoints.http.ProdutoHttp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring")
public interface ProdutoHttpMapper {

    ProdutoHttpMapper INSTANCE = Mappers.getMapper( ProdutoHttpMapper.class );

    @Mapping(target = "categoria.codigo", source = "produto.categoria.codigo")
    ProdutoHttp entityToHttp(Produto produto);

    @Mapping(target = "categoria.codigo", source = "produtoHttp.categoria.codigo")
    Produto httpToEntity(ProdutoHttp produtoHttp);

    List<ProdutoHttp> entityListToHttpList(List<Produto> produtoList);
    List<Produto> httpListToEntityList(List<ProdutoHttp> produtoHttpList);
}
