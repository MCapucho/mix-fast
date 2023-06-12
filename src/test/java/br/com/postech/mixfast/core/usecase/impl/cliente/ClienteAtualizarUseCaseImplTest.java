package br.com.postech.mixfast.core.usecase.impl.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.gateway.ClienteGateway;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteBuscarPorCodigoUseCase;
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
class ClienteAtualizarUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private ClienteAtualizarUseCaseImpl clienteAtualizarUseCaseImpl;
    @Mock
    private ClienteGateway clienteGateway;
    @Mock
    private ClienteBuscarPorCodigoUseCase clienteBuscarPorCodigoUseCase;

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
    void deveAtualizarUmClienteComSucesso() {
        when(clienteBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(clienteResponse);

        when(clienteGateway.cadastrarOuAtualizar(any()))
                .thenReturn(clienteResponse);

        Cliente clienteAtualizado = clienteAtualizarUseCaseImpl.atualizar(CODIGO, clienteRequest);

        Assertions.assertNotNull(clienteAtualizado);
        Assertions.assertEquals(clienteRequest.getNome(), clienteAtualizado.getNome());
        Assertions.assertEquals(clienteRequest.getCpf(), clienteAtualizado.getCpf());
        Assertions.assertEquals(clienteRequest.getEmail(), clienteAtualizado.getEmail());
    }

    @Test
    void deveAtualizarUmClienteComSucesso_NomeNull() {
        clienteRequest.setNome(null);

        when(clienteBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(clienteResponse);

        when(clienteGateway.cadastrarOuAtualizar(any()))
                .thenReturn(clienteResponse);

        Cliente clienteAtualizada = clienteAtualizarUseCaseImpl.atualizar(CODIGO, clienteRequest);

        Assertions.assertNotNull(clienteAtualizada);
    }

    @Test
    void deveAtualizarUmClienteComSucesso_EmailNull() {
        clienteRequest.setEmail(null);

        when(clienteBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(clienteResponse);

        when(clienteGateway.cadastrarOuAtualizar(any()))
                .thenReturn(clienteResponse);

        Cliente clienteAtualizado = clienteAtualizarUseCaseImpl.atualizar(CODIGO, clienteRequest);

        Assertions.assertNotNull(clienteAtualizado);
    }
}