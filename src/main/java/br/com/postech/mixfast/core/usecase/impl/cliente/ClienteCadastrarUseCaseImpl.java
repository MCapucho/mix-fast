package br.com.postech.mixfast.core.usecase.impl.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.exception.cliente.ClienteBadRequestException;
import br.com.postech.mixfast.core.exception.cliente.ClienteDuplicatedException;
import br.com.postech.mixfast.core.gateway.ClienteGateway;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteCadastrarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClienteCadastrarUseCaseImpl implements ClienteCadastrarUseCase {

    private final ClienteGateway clienteGateway;

    @Override
    public Cliente cadastrar(Cliente cliente) {
        if (clienteGateway.encontrarPorCpf(cliente.getCpf()) || clienteGateway.encontrarPorEmail(cliente.getEmail())) {
            throw new ClienteDuplicatedException("Cliente informado já cadastrado");
        }

        Cliente clienteCadastrado = clienteGateway.cadastrarOuAtualizar(cliente);

        if (clienteCadastrado == null) {
            throw new ClienteBadRequestException("Cadastro de cliente não foi concluído");
        }

        return clienteCadastrado;
    }
}
