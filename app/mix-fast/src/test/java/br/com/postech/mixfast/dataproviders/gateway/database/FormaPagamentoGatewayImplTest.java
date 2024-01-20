package br.com.postech.mixfast.dataproviders.gateway.database;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
import br.com.postech.mixfast.dataproviders.model.db.FormaPagamentoDB;
import br.com.postech.mixfast.dataproviders.model.mapper.FormaPagamentoDBMapper;
import br.com.postech.mixfast.dataproviders.repository.FormaPagamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FormaPagamentoGatewayImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();
    public static final String NOME_FORMA_PAGAMENTO = "Dinheiro";

    @InjectMocks
    private FormaPagamentoGatewayImpl formaPagamentoGatewayImpl;
    @Mock
    private FormaPagamentoRepository formaPagamentoRepository;
    @Mock
    private FormaPagamentoDBMapper formaPagamentoDBMapper;

    private FormaPagamento formaPagamentoRequest;
    private FormaPagamento formaPagamentoResponse;
    private FormaPagamentoDB formaPagamentoDBRequest;
    private FormaPagamentoDB formaPagamentoDBResponse;

    @BeforeEach
    void setUp() {
        formaPagamentoRequest = FormaPagamento.builder()
                .descricao("Forma Pagamento Teste")
                .build();

        formaPagamentoResponse = FormaPagamento.builder()
                .codigo(UUID.randomUUID().toString())
                .descricao("Forma Pagamento Teste")
                .build();

        formaPagamentoDBRequest = FormaPagamentoDB.builder()
                .descricao("Forma Pagamento Teste")
                .build();

        formaPagamentoDBResponse = FormaPagamentoDB.builder()
                .codigo(UUID.randomUUID().toString())
                .descricao("Forma Pagamento Teste")
                .build();
    }

    @Test
    void cadastrarOuAtualizar_Sucesso() {
        when(formaPagamentoDBMapper.entityToDB(any(FormaPagamento.class)))
                .thenReturn(formaPagamentoDBRequest);

        when(formaPagamentoRepository.save(any(FormaPagamentoDB.class)))
                .thenReturn(formaPagamentoDBResponse);

        when(formaPagamentoDBMapper.dbToEntity(any(FormaPagamentoDB.class)))
                .thenReturn(formaPagamentoResponse);

        FormaPagamento formaPagamentoCadastrada = formaPagamentoGatewayImpl.cadastrarOuAtualizar(formaPagamentoRequest);

        Assertions.assertNotNull(formaPagamentoCadastrada);
        Assertions.assertEquals(formaPagamentoRequest.getDescricao(), formaPagamentoCadastrada.getDescricao());
    }

    @Test
    void cadastrarOuAtualizar_Erro() {
        when(formaPagamentoDBMapper.entityToDB(any(FormaPagamento.class)))
                .thenReturn(formaPagamentoDBRequest);

        when(formaPagamentoRepository.save(any(FormaPagamentoDB.class)))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                formaPagamentoGatewayImpl.cadastrarOuAtualizar(formaPagamentoRequest));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarTodas_Sucesso() {
        when(formaPagamentoRepository.findAll())
                .thenReturn(List.of(formaPagamentoDBResponse));

        when(formaPagamentoDBMapper.dbToEntity(any(FormaPagamentoDB.class)))
                .thenReturn(formaPagamentoResponse);

        List<FormaPagamento> listaFormaPagamentoEncontrada = formaPagamentoGatewayImpl.buscarTodas();

        Assertions.assertNotNull(listaFormaPagamentoEncontrada);
        Assertions.assertEquals(1, listaFormaPagamentoEncontrada.size());
    }

    @Test
    void buscarTodas_Erro() {
        when(formaPagamentoRepository.findAll())
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                formaPagamentoGatewayImpl.buscarTodas());

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarPorCodigo_Sucesso() {
        when(formaPagamentoRepository.findById(anyString()))
                .thenReturn(Optional.of(formaPagamentoDBResponse));

        when(formaPagamentoDBMapper.dbToEntity(any(FormaPagamentoDB.class)))
                .thenReturn(formaPagamentoResponse);

        FormaPagamento formaPagamentoEncontrada = formaPagamentoGatewayImpl.buscarPorCodigo(CODIGO);

        Assertions.assertNotNull(formaPagamentoEncontrada);
        Assertions.assertEquals(formaPagamentoRequest.getDescricao(), formaPagamentoEncontrada.getDescricao());
    }

    @Test
    void buscarPorCodigo_Erro() {
        when(formaPagamentoRepository.findById(anyString()))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                formaPagamentoGatewayImpl.buscarPorCodigo(CODIGO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void deletarPorCodigo_Sucesso() {
        doNothing().when(formaPagamentoRepository)
                .deleteById(CODIGO);

        formaPagamentoGatewayImpl.deletarPorCodigo(CODIGO);

        verify(formaPagamentoRepository, times(1)).deleteById(CODIGO);
    }

    @Test
    void deletarPorCodigo_Erro() {
        doThrow(new ArithmeticException()).when(formaPagamentoRepository)
                .deleteById(anyString());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                formaPagamentoGatewayImpl.deletarPorCodigo(CODIGO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void encontrarPorDescricao_Sucesso() {
        when(formaPagamentoRepository.existsByDescricao(anyString()))
                .thenReturn(Boolean.TRUE);

        Boolean formaPagamentoNomeExistente = formaPagamentoGatewayImpl.encontrarPorDescricao(NOME_FORMA_PAGAMENTO);

        Assertions.assertTrue(formaPagamentoNomeExistente);
    }

    @Test
    void encontrarPorDescricao_Erro() {
        when(formaPagamentoRepository.existsByDescricao(anyString()))
                .thenThrow(new NullPointerException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                formaPagamentoGatewayImpl.encontrarPorDescricao(NOME_FORMA_PAGAMENTO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}