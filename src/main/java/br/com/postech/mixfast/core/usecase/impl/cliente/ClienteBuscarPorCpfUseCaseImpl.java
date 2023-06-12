package br.com.postech.mixfast.core.usecase.impl.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.exception.cliente.ClienteNotFoundException;
import br.com.postech.mixfast.core.gateway.ClienteGateway;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteBuscarPorCpfUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClienteBuscarPorCpfUseCaseImpl implements ClienteBuscarPorCpfUseCase {

    private final ClienteGateway clienteGateway;

    @Override
    public Cliente buscarPorCpf(String cpf) {
        Cliente cliente = clienteGateway.buscarPorCpf(cpf);

        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente não encontrado com o CPF informado");
        }

        return cliente;
    }
}
