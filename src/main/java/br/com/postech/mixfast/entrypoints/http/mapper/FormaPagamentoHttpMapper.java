package br.com.postech.mixfast.entrypoints.http.mapper;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.entrypoints.http.FormaPagamentoHttp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface FormaPagamentoHttpMapper {

    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "descricao", source = "descricao")
    FormaPagamentoHttp entityToHttp(FormaPagamento formaPagamento);

    @Mapping(target = "codigo", ignore = true)
    @Mapping(target = "descricao", source = "descricao")
    FormaPagamento httpToEntity(FormaPagamentoHttp formaPagamentoHttp);
}
