package br.com.postech.mixfast.core.usecase.impl.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.exception.cliente.ClienteNotFoundException;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteBuscarPorCodigoUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private ClienteBuscarPorCodigoUseCaseImpl clienteBuscarPorCodigoUseCaseImpl;
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
    void deveBuscarPorCodigoClienteComSucesso() {
        when(clienteGateway.buscarPorCodigo(anyString()))
                .thenReturn(clienteResponse);

        Cliente clienteCadastrado = clienteBuscarPorCodigoUseCaseImpl.buscarPorCodigo(CODIGO);

        Assertions.assertNotNull(clienteCadastrado);
        Assertions.assertEquals(clienteRequest.getNome(), clienteCadastrado.getNome());
        Assertions.assertEquals(clienteRequest.getCpf(), clienteCadastrado.getCpf());
        Assertions.assertEquals(clienteRequest.getEmail(), clienteCadastrado.getEmail());
    }

    @Test
    void naoDeveBuscarPorCodigoCliente_Erro() {
        Exception exception = Assertions.assertThrows(ClienteNotFoundException.class, () ->
                clienteBuscarPorCodigoUseCaseImpl.buscarPorCodigo(CODIGO));

        String mensagemEsperada = "Cliente não encontrado com o código informado";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}