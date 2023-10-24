package br.com.postech.mixfast.core.usecase.impl.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.exception.cliente.ClienteNotFoundException;
import br.com.postech.mixfast.core.gateway.ClienteGateway;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteBuscarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClienteBuscarPorCodigoUseCaseImpl implements ClienteBuscarPorCodigoUseCase {

    private final ClienteGateway clienteGateway;

    @Override
    public Cliente buscarPorCodigo(String codigo) {
        Cliente cliente = clienteGateway.buscarPorCodigo(codigo);

        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente não encontrado com o código informado");
        }

        return cliente;
    }
}
