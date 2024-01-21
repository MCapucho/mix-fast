package br.com.postech.mixfast.core.usecase.impl.formapagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.exception.formapagamento.FormaPagamentoListEmptyException;
import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.formapagamento.FormaPagamentoBuscarTodasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FormaPagamentoBuscarTodasUseCaseImpl implements FormaPagamentoBuscarTodasUseCase {

    private final FormaPagamentoGateway formaPagamentoGateway;

    @Override
    public List<FormaPagamento> buscarTodas() {
        List<FormaPagamento> listaFormasPagamento = formaPagamentoGateway.buscarTodas();

        if (listaFormasPagamento.isEmpty()) {
            throw new FormaPagamentoListEmptyException("Lista de formas de pagamento em branco");
        }

        return listaFormasPagamento;
    }
}
