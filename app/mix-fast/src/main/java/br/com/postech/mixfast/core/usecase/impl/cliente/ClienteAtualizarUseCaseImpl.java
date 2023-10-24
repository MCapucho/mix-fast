package br.com.postech.mixfast.core.usecase.impl.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.gateway.ClienteGateway;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteAtualizarUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteBuscarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClienteAtualizarUseCaseImpl implements ClienteAtualizarUseCase {

    private final ClienteGateway clienteGateway;
    private final ClienteBuscarPorCodigoUseCase clienteBuscarPorCodigoUseCase;

    @Override
    public Cliente atualizar(String codigo, Cliente cliente) {
        Cliente clienteEncontrado = clienteBuscarPorCodigoUseCase.buscarPorCodigo(codigo);

        clienteEncontrado.setNome(cliente.getNome() != null ? cliente.getNome() : clienteEncontrado.getNome());
        clienteEncontrado.setEmail(cliente.getEmail() != null ? cliente.getEmail() : clienteEncontrado.getEmail());

        return clienteGateway.cadastrarOuAtualizar(clienteEncontrado);
    }
}
