package br.com.postech.mixfast.core.usecase.impl.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.gateway.ClienteGateway;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteBuscarPorCodigoUseCase;
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
class ClienteDeletarPorCodigoUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private ClienteDeletarPorCodigoUseCaseImpl clienteDeletarPorCodigoUseCaseImpl;
    @Mock
    private ClienteGateway clienteGateway;
    @Mock
    private ClienteBuscarPorCodigoUseCase clienteBuscarPorCodigoUseCase;

    private Cliente clienteResponse;

    @BeforeEach
    void setUp() {
        clienteResponse = Cliente.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Cliente Teste")
                .cpf("26707200054")
                .email("teste@mixfast.com.br")
                .build();
    }

    @Test
    void deveDeletarUmClienteComSucesso() {
        when(clienteBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(clienteResponse);

        doNothing().when(clienteGateway)
                .deletarPorCodigo(CODIGO);

        clienteDeletarPorCodigoUseCaseImpl.deletarPorCodigo(CODIGO);

        verify(clienteBuscarPorCodigoUseCase, times(1)).buscarPorCodigo(CODIGO);
        verify(clienteGateway, times(1)).deletarPorCodigo(CODIGO);
    }
}