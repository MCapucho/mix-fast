package br.com.postech.mixfast.dataproviders.model.mapper;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.dataproviders.model.db.CategoriaDB;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface CategoriaDBMapper {

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "produtos", ignore = true)
    CategoriaDB entityToDB(Categoria categoria);

    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "nome", source = "nome")
    Categoria dbToEntity(CategoriaDB categoriaDB);
}
