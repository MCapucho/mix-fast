package br.com.postech.mixfast.entrypoints.http.mapper;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.entrypoints.http.CategoriaHttp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring")
public interface CategoriaHttpMapper {

    CategoriaHttpMapper INSTANCE = Mappers.getMapper( CategoriaHttpMapper.class );

    CategoriaHttp entityToHttp(Categoria categoria);

    @Mapping(target = "codigo", ignore = true)
    Categoria httpToEntity(CategoriaHttp categoriaHttp);

    List<CategoriaHttp> entityListToHttpList(List<Categoria> categoriaList);
    List<Categoria> httpListToEntityList(List<CategoriaHttp> categoriaHttpList);
}
