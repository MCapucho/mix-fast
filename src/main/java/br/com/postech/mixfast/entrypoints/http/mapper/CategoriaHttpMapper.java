package br.com.postech.mixfast.entrypoints.http.mapper;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.entrypoints.http.CategoriaHttp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface CategoriaHttpMapper {

    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "nome", source = "nome")
    CategoriaHttp entityToHttp(Categoria categoria);

    @Mapping(target = "codigo", ignore = true)
    @Mapping(target = "nome", source = "nome")
    Categoria httpToEntity(CategoriaHttp categoriaHttp);
}
