package br.com.postech.mixfast.entrypoints.http.mapper;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.entrypoints.http.ClienteHttp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ClienteHttpMapper {

    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "email", source = "email")
    ClienteHttp entityToHttp(Cliente cliente);

    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "email", source = "email")
    Cliente httpToEntity(ClienteHttp clienteHttp);
}
