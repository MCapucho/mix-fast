package br.com.postech.mixfast.core.usecase.impl.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.exception.cliente.ClienteListEmptyException;
import br.com.postech.mixfast.core.gateway.ClienteGateway;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteBuscarTodosUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClienteBuscarTodosUseCaseImpl implements ClienteBuscarTodosUseCase {

    private final ClienteGateway clienteGateway;

    @Override
    public List<Cliente> buscarTodos() {
        List<Cliente> listaClientes = clienteGateway.buscarTodos();

        if (listaClientes.isEmpty()) {
            throw new ClienteListEmptyException("Lista de clientes em branco");
        }

        return listaClientes;
    }
}
