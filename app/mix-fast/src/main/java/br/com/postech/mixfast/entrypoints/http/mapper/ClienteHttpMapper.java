package br.com.postech.mixfast.entrypoints.http.mapper;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.entrypoints.http.ClienteHttp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring")
public interface ClienteHttpMapper {

    ClienteHttpMapper INSTANCE = Mappers.getMapper( ClienteHttpMapper.class );

    ClienteHttp entityToHttp(Cliente cliente);

    Cliente httpToEntity(ClienteHttp clienteHttp);

    List<ClienteHttp> entityListToHttpList(List<Cliente> clienteList);
    List<Cliente> httpListToEntityList(List<ClienteHttp> clienteHttpList);
}
