package br.com.postech.mixfast.core.usecase.impl.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.exception.cliente.ClienteBadRequestException;
import br.com.postech.mixfast.core.exception.cliente.ClienteDuplicatedException;
import br.com.postech.mixfast.core.gateway.ClienteGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteCadastrarUseCaseImplTest {

    @InjectMocks
    private ClienteCadastrarUseCaseImpl clienteCadastrarUseCaseImpl;
    @Mock
    private ClienteGateway clienteGateway;

    private Cliente clienteRequest;
    private Cliente clienteResponse;

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
    }

    @Test
    void deveCadastrarUmClienteComSucesso() {
        when(clienteGateway.encontrarPorCpf(anyString()))
                .thenReturn(Boolean.FALSE);

        when(clienteGateway.encontrarPorEmail(anyString()))
                .thenReturn(Boolean.FALSE);

        when(clienteGateway.cadastrarOuAtualizar(any()))
                .thenReturn(clienteResponse);

        Cliente clienteCadastrado = clienteCadastrarUseCaseImpl.cadastrar(clienteRequest);

        Assertions.assertNotNull(clienteCadastrado);
        Assertions.assertEquals(clienteRequest.getNome(), clienteCadastrado.getNome());
        Assertions.assertEquals(clienteRequest.getCpf(), clienteCadastrado.getCpf());
        Assertions.assertEquals(clienteRequest.getEmail(), clienteCadastrado.getEmail());
    }

    @Test
    void naoDeveCadastrarUmCliente_ErroBadRequest() {
        Exception exception = Assertions.assertThrows(ClienteBadRequestException.class, () ->
                clienteCadastrarUseCaseImpl.cadastrar(clienteRequest));

        String mensagemEsperada = "Cadastro de cliente não foi concluído";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void naoDeveCadastrarUmCliente_ErroCpfDuplicated() {
        when(clienteGateway.encontrarPorCpf(anyString()))
                .thenReturn(Boolean.TRUE);

        Exception exception = Assertions.assertThrows(ClienteDuplicatedException.class, () ->
                clienteCadastrarUseCaseImpl.cadastrar(clienteRequest));

        String mensagemEsperada = "Cliente informado já cadastrado";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void naoDeveCadastrarUmCliente_ErroEmailDuplicated() {
        when(clienteGateway.encontrarPorEmail(anyString()))
                .thenReturn(Boolean.TRUE);

        Exception exception = Assertions.assertThrows(ClienteDuplicatedException.class, () ->
                clienteCadastrarUseCaseImpl.cadastrar(clienteRequest));

        String mensagemEsperada = "Cliente informado já cadastrado";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}