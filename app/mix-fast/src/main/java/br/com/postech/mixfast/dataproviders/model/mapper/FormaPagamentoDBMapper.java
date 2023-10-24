package br.com.postech.mixfast.dataproviders.model.mapper;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.dataproviders.model.db.FormaPagamentoDB;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface FormaPagamentoDBMapper {

    @Mapping(target = "descricao", source = "descricao")
    FormaPagamentoDB entityToDB(FormaPagamento formaPagamento);

    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "descricao", source = "descricao")
    FormaPagamento dbToEntity(FormaPagamentoDB formaPagamentoDB);
}
