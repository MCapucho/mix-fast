package br.com.postech.mixfast.dataproviders.model.mapper;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.dataproviders.model.db.PedidoDB;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface PedidoDBMapper {

    PedidoDB entityToDB(Pedido pedido);

    Pedido dbToEntity(PedidoDB pedidoDB);
}
