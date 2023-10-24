package br.com.postech.mixfast.core.usecase.impl.cliente;

import br.com.postech.mixfast.core.gateway.ClienteGateway;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.ClienteDeletarPorCodigoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClienteDeletarPorCodigoUseCaseImpl implements ClienteDeletarPorCodigoUseCase {

    private final ClienteGateway clienteGateway;
    private final ClienteBuscarPorCodigoUseCase clienteBuscarPorCodigoUseCase;

    @Override
    public void deletarPorCodigo(String codigo) {
        clienteBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        clienteGateway.deletarPorCodigo(codigo);
    }
}
