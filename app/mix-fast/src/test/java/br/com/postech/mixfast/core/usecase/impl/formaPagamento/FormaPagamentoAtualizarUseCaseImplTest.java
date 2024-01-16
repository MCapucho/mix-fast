package br.com.postech.mixfast.core.usecase.impl.formaPagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.FormaPagamentoBuscarPorCodigoUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormaPagamentoAtualizarUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private FormaPagamentoAtualizarUseCaseImpl formaPagamentoAtualizarUseCaseImpl;
    @Mock
    private FormaPagamentoGateway formaPagamentoGateway;
    @Mock
    private FormaPagamentoBuscarPorCodigoUseCase formaPagamentoBuscarPorCodigoUseCase;

    private FormaPagamento formaPagamentoRequest;
    private FormaPagamento formaPagamentoResponse;

    @BeforeEach
    void setUp() {
        formaPagamentoRequest = FormaPagamento.builder()
                .descricao("Forma Pagamento Teste")
                .build();

        formaPagamentoResponse = FormaPagamento.builder()
                .codigo(UUID.randomUUID().toString())
                .descricao("Forma Pagamento Teste")
                .build();
    }

    @Test
    void deveAtualizarUmaFormaPagamentoComSucesso() {
        when(formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(formaPagamentoResponse);

        when(formaPagamentoGateway.cadastrarOuAtualizar(any()))
                .thenReturn(formaPagamentoResponse);

        FormaPagamento formaPagamentoAtualizada = formaPagamentoAtualizarUseCaseImpl.atualizar(CODIGO, formaPagamentoRequest);

        Assertions.assertNotNull(formaPagamentoAtualizada);
        Assertions.assertEquals(formaPagamentoRequest.getDescricao(), formaPagamentoAtualizada.getDescricao());
    }

    @Test
    void deveAtualizarUmaCategoriaComSucesso_NomeNull() {
        formaPagamentoRequest.setDescricao(null);

        when(formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(formaPagamentoResponse);

        when(formaPagamentoGateway.cadastrarOuAtualizar(any()))
                .thenReturn(formaPagamentoResponse);

        FormaPagamento formaPagamentoAtualizada = formaPagamentoAtualizarUseCaseImpl.atualizar(CODIGO, formaPagamentoRequest);

        Assertions.assertNotNull(formaPagamentoAtualizada);
    }
}