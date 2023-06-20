package br.com.postech.mixfast.dataproviders.model.mapper;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.dataproviders.model.db.ClienteDB;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ClienteDBMapper {

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "email", source = "email")
    ClienteDB entityToDB(Cliente cliente);

    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "email", source = "email")
    Cliente dbToEntity(ClienteDB clienteDB);
}
