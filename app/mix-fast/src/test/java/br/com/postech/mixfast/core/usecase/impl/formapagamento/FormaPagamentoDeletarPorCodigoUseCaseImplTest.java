package br.com.postech.mixfast.core.usecase.impl.formapagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import br.com.postech.mixfast.core.usecase.interfaces.formapagamento.FormaPagamentoBuscarPorCodigoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FormaPagamentoDeletarPorCodigoUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private FormaPagamentoDeletarPorCodigoUseCaseImpl formaPagamentoDeletarPorCodigoUseCaseImpl;
    @Mock
    private FormaPagamentoGateway formaPagamentoGateway;
    @Mock
    private FormaPagamentoBuscarPorCodigoUseCase formaPagamentoBuscarPorCodigoUseCase;

    private FormaPagamento formaPagamentoResponse;

    @BeforeEach
    void setUp() {
        formaPagamentoResponse = FormaPagamento.builder()
                .codigo(UUID.randomUUID().toString())
                .descricao("Forma Pagamento Teste")
                .build();
    }

    @Test
    void deveDeletarUmaFormaPagamentoComSucesso() {
        when(formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(formaPagamentoResponse);

        doNothing().when(formaPagamentoGateway)
                .deletarPorCodigo(CODIGO);

        formaPagamentoDeletarPorCodigoUseCaseImpl.deletarPorCodigo(CODIGO);

        verify(formaPagamentoBuscarPorCodigoUseCase, times(1)).buscarPorCodigo(CODIGO);
        verify(formaPagamentoGateway, times(1)).deletarPorCodigo(CODIGO);
    }
}