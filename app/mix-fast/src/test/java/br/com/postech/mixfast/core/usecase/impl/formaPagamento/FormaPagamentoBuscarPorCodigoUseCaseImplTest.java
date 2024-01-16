package br.com.postech.mixfast.core.usecase.impl.formaPagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.exception.formaPagamento.FormaPagamentoNotFoundException;
import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormaPagamentoBuscarPorCodigoUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private FormaPagamentoBuscarPorCodigoUseCaseImpl formaPagamentoBuscarPorCodigoUseCaseImpl;
    @Mock
    private FormaPagamentoGateway formaPagamentoGateway;

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
    void deveBuscarPorCodigoFormaPagamentoComSucesso() {
        when(formaPagamentoGateway.buscarPorCodigo(anyString()))
                .thenReturn(formaPagamentoResponse);

        FormaPagamento formaPagamentoEncontrada = formaPagamentoBuscarPorCodigoUseCaseImpl.buscarPorCodigo(CODIGO);

        Assertions.assertNotNull(formaPagamentoEncontrada);
        Assertions.assertEquals(formaPagamentoRequest.getDescricao(), formaPagamentoEncontrada.getDescricao());
    }

    @Test
    void naoDeveBuscarPorCodigoFormaPagamento_Erro() {
        Exception exception = Assertions.assertThrows(FormaPagamentoNotFoundException.class, () ->
                formaPagamentoBuscarPorCodigoUseCaseImpl.buscarPorCodigo(CODIGO));

        String mensagemEsperada = "Forma de pagamento não encontrada com o código informado";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}