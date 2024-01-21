package br.com.postech.mixfast.core.usecase.impl.formapagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.exception.formapagamento.FormaPagamentoBadRequestException;
import br.com.postech.mixfast.core.exception.formapagamento.FormaPagamentoDuplicatedException;
import br.com.postech.mixfast.core.gateway.FormaPagamentoGateway;
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
class FormaPagamentoCadastrarUseCaseImplTest {

    @InjectMocks
    private FormaPagamentoCadastrarUseCaseImpl formaPagamentoCadastrarUseCaseImpl;
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
    void deveCadastrarUmaFormaPagamentoComSucesso() {
        when(formaPagamentoGateway.cadastrarOuAtualizar(any()))
                .thenReturn(formaPagamentoResponse);

        FormaPagamento formaPagamentoCadastrada = formaPagamentoCadastrarUseCaseImpl.cadastrar(formaPagamentoRequest);

        Assertions.assertNotNull(formaPagamentoCadastrada);
        Assertions.assertEquals(formaPagamentoRequest.getDescricao(), formaPagamentoCadastrada.getDescricao());
    }

    @Test
    void naoDeveCadastrarUmaFormaPagamento_Erro() {
        Exception exception = Assertions.assertThrows(FormaPagamentoBadRequestException.class, () ->
                formaPagamentoCadastrarUseCaseImpl.cadastrar(formaPagamentoRequest));

        String mensagemEsperada = "Cadastro de forma de pagamento não foi concluído";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void naoDeveCadastrarUmaFormaPagamento_Erro_FormaPagamentoExistente() {
        when(formaPagamentoGateway.encontrarPorDescricao(anyString()))
                .thenReturn(Boolean.TRUE);

        Exception exception = Assertions.assertThrows(FormaPagamentoDuplicatedException.class, () ->
                formaPagamentoCadastrarUseCaseImpl.cadastrar(formaPagamentoRequest));

        String mensagemEsperada = "Forma de Pagamento informada já cadastrada";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}