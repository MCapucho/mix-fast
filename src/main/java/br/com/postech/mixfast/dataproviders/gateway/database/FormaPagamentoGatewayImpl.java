package br.com.postech.mixfast.dataproviders.gateway.database;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
import br.com.postech.mixfast.dataproviders.model.db.FormaPagamentoDB;
import br.com.postech.mixfast.dataproviders.model.mapper.FormaPagamentoDBMapper;
import br.com.postech.mixfast.dataproviders.repository.FormaPagamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j2
@Service
public class FormaPagamentoGatewayImpl implements FormaPagamentoGateway {

    private static final String BANCO_DE_DADOS = "Erro na comunicação com o banco de dados";

    private final FormaPagamentoDBMapper formaPagamentoDBMapper;
    private final FormaPagamentoRepository formaPagamentoRepository;

    @Override
    public FormaPagamento cadastrar(FormaPagamento formaPagamento) {
        try {
            FormaPagamentoDB formaPagamentoDB = formaPagamentoRepository.save(formaPagamentoDBMapper.entityToDB(formaPagamento));
            return formaPagamentoDBMapper.dbToEntity(formaPagamentoDB);
        } catch (Exception e) {
            log.error("Erro ao cadastrar/atualizar uma forma de pagamento", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Override
    public Boolean encontrarPorDescricao(String descricao) {
        try {
            return formaPagamentoRepository.existsByDescricao(descricao);
        } catch (Exception e) {
            log.error("Erro ao buscar uma forma de pagamento por descrição", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }
}
