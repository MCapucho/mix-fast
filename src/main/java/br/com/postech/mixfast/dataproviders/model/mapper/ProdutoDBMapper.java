package br.com.postech.mixfast.dataproviders.model.mapper;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.dataproviders.model.ProdutoDB;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ProdutoDBMapper {

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "preco", source = "preco")
    @Mapping(target = "categoria.codigo", source = "produto.categoria.codigo")
    @Mapping(target = "categoria.nome", ignore = true)
    ProdutoDB entityToDB(Produto produto);

    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "preco", source = "preco")
    @Mapping(target = "categoria.codigo", source = "produtoDB.categoria.codigo")
    @Mapping(target = "categoria.nome", source = "produtoDB.categoria.nome")
    Produto dbToEntity(ProdutoDB produtoDB);
}
