package br.com.postech.mixfast.core.usecase.impl.formaPagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.exception.formaPagamento.FormaPagamentoListEmptyException;
import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormaPagamentoBuscarTodasUseCaseImplTest {

    @InjectMocks
    private FormaPagamentoBuscarTodasUseCaseImpl formaPagamentoBuscarTodasUseCaseImpl;
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
    void deveBuscarTodasFormaPagamentoComSucesso() {
        when(formaPagamentoGateway.buscarTodas())
                .thenReturn(List.of(formaPagamentoResponse));

        List<FormaPagamento> listaFormasPagamento = formaPagamentoBuscarTodasUseCaseImpl.buscarTodas();

        Assertions.assertNotNull(listaFormasPagamento);
        Assertions.assertEquals(formaPagamentoRequest.getDescricao(), listaFormasPagamento.get(0).getDescricao());
    }

    @Test
    void naoDeveBuscarTodasFormaPagamento_Erro() {
        Exception exception = Assertions.assertThrows(FormaPagamentoListEmptyException.class, () ->
                formaPagamentoBuscarTodasUseCaseImpl.buscarTodas());

        String mensagemEsperada = "Lista de formas de pagamento em branco";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}