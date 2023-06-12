package br.com.postech.mixfast.core.usecase.impl.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.exception.cliente.ClienteListEmptyException;
import br.com.postech.mixfast.core.gateway.ClienteGateway;
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
class ClienteBuscarTodosUseCaseImplTest {

    @InjectMocks
    private ClienteBuscarTodosUseCaseImpl clienteBuscarTodosUseCaseImpl;
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
    void deveBuscarTodosClienteComSucesso() {
        when(clienteGateway.buscarTodos())
                .thenReturn(List.of(clienteResponse));

        List<Cliente> listaClientes = clienteBuscarTodosUseCaseImpl.buscarTodos();

        Assertions.assertNotNull(listaClientes);
        Assertions.assertEquals(clienteRequest.getNome(), listaClientes.get(0).getNome());
        Assertions.assertEquals(clienteRequest.getCpf(), listaClientes.get(0).getCpf());
        Assertions.assertEquals(clienteRequest.getEmail(), listaClientes.get(0).getEmail());
    }

    @Test
    void naoDeveBuscarTodosCliente_Erro() {
        Exception exception = Assertions.assertThrows(ClienteListEmptyException.class, () -> {
            clienteBuscarTodosUseCaseImpl.buscarTodos();
        });

        String mensagemEsperada = "Lista de clientes em branco";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}