package br.com.postech.mixfast.dataproviders.gateway.database;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
import br.com.postech.mixfast.dataproviders.model.db.ClienteDB;
import br.com.postech.mixfast.dataproviders.model.mapper.ClienteDBMapper;
import br.com.postech.mixfast.dataproviders.repository.ClienteRepository;
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
class ClienteGatewayImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();
    public static final String EMAIL_CLIENTE = "teste@mixfast.com.br";
    public static final String CPF_CLIENTE = "123.456.789-00";

    @InjectMocks
    private ClienteGatewayImpl clienteGatewayImpl;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClienteDBMapper clienteDBMapper;

    private Cliente clienteRequest;
    private Cliente clienteResponse;
    private ClienteDB clienteDBRequest;
    private ClienteDB clienteDBResponse;

    @BeforeEach
    void setUp() {
        clienteRequest = Cliente.builder()
                .nome("Cliente Teste")
                .cpf("26707200054")
                .email("teste@mixfast.com.br")
                .build();

        clienteResponse = Cliente.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Cliente Teste")
                .cpf("26707200054")
                .email("teste@mixfast.com.br")
                .build();

        clienteDBRequest = ClienteDB.builder()
                .nome("Cliente Teste")
                .cpf("26707200054")
                .email("teste@mixfast.com.br")
                .build();

        clienteDBResponse = ClienteDB.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Cliente Teste")
                .cpf("26707200054")
                .email("teste@mixfast.com.br")
                .build();
    }

    @Test
    void cadastrarOuAtualizar_Sucesso() {
        when(clienteDBMapper.entityToDB(any(Cliente.class)))
                .thenReturn(clienteDBRequest);

        when(clienteRepository.save(any(ClienteDB.class)))
                .thenReturn(clienteDBResponse);

        when(clienteDBMapper.dbToEntity(any(ClienteDB.class)))
                .thenReturn(clienteResponse);

        Cliente clienteCadastrado = clienteGatewayImpl.cadastrarOuAtualizar(clienteRequest);

        Assertions.assertNotNull(clienteCadastrado);
        Assertions.assertEquals(clienteRequest.getNome(), clienteCadastrado.getNome());
    }

    @Test
    void cadastrarOuAtualizar_Erro() {
        when(clienteDBMapper.entityToDB(any(Cliente.class)))
                .thenReturn(clienteDBResponse);

        when(clienteRepository.save(any(ClienteDB.class)))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                clienteGatewayImpl.cadastrarOuAtualizar(clienteRequest));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarTodos_Sucesso() {
        when(clienteRepository.findAll())
                .thenReturn(List.of(clienteDBResponse));

        when(clienteDBMapper.dbToEntity(any(ClienteDB.class)))
                .thenReturn(clienteResponse);

        List<Cliente> listaClienteEncontrado = clienteGatewayImpl.buscarTodos();

        Assertions.assertNotNull(listaClienteEncontrado);
        Assertions.assertEquals(1, listaClienteEncontrado.size());
    }

    @Test
    void buscarTodos_Erro() {
        when(clienteRepository.findAll())
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                clienteGatewayImpl.buscarTodos());

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarPorCodigo_Sucesso() {
        when(clienteRepository.findById(anyString()))
                .thenReturn(Optional.of(clienteDBResponse));

        when(clienteDBMapper.dbToEntity(any(ClienteDB.class)))
                .thenReturn(clienteResponse);

        Cliente clienteEncontrado = clienteGatewayImpl.buscarPorCodigo(CODIGO);

        Assertions.assertNotNull(clienteEncontrado);
        Assertions.assertEquals(clienteRequest.getNome(), clienteEncontrado.getNome());
    }

    @Test
    void buscarPorCodigo_Erro() {
        when(clienteRepository.findById(anyString()))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                clienteGatewayImpl.buscarPorCodigo(CODIGO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void deletarPorCodigo_Sucesso() {
        doNothing().when(clienteRepository)
                .deleteById(CODIGO);

        clienteGatewayImpl.deletarPorCodigo(CODIGO);

        verify(clienteRepository, times(1)).deleteById(CODIGO);
    }

    @Test
    void deletarPorCodigo_Erro() {
        doThrow(new ArithmeticException()).when(clienteRepository)
                .deleteById(anyString());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                clienteGatewayImpl.deletarPorCodigo(CODIGO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarPorCpf_Sucesso() {
        when(clienteRepository.findByCpf(anyString()))
                .thenReturn(clienteDBResponse);

        when(clienteDBMapper.dbToEntity(any(ClienteDB.class)))
                .thenReturn(clienteResponse);

        Cliente clienteEncontrado = clienteGatewayImpl.buscarPorCpf(CPF_CLIENTE);

        Assertions.assertNotNull(clienteEncontrado);
        Assertions.assertEquals(clienteRequest.getNome(), clienteEncontrado.getNome());
    }

    @Test
    void buscarPorCpf_Erro() {
        when(clienteRepository.findByCpf(anyString()))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                clienteGatewayImpl.buscarPorCpf(CODIGO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void encontrarPorCpf_Sucesso() {
        when(clienteRepository.existsByCpf(anyString()))
                .thenReturn(Boolean.TRUE);

        Boolean clienteCPFExistente = clienteGatewayImpl.encontrarPorCpf(CPF_CLIENTE);

        Assertions.assertTrue(clienteCPFExistente);
    }

    @Test
    void encontrarPorCpf_Erro() {
        when(clienteRepository.existsByCpf(anyString()))
                .thenThrow(new NullPointerException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                clienteGatewayImpl.encontrarPorCpf(CPF_CLIENTE));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void encontrarPorEmail_Sucesso() {
        when(clienteRepository.existsByEmail(anyString()))
                .thenReturn(Boolean.TRUE);

        Boolean clienteEmailExistente = clienteGatewayImpl.encontrarPorEmail(EMAIL_CLIENTE);

        Assertions.assertTrue(clienteEmailExistente);
    }

    @Test
    void encontrarPorEmail_Erro() {
        when(clienteRepository.existsByEmail(anyString()))
                .thenThrow(new NullPointerException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                clienteGatewayImpl.encontrarPorEmail(EMAIL_CLIENTE));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}