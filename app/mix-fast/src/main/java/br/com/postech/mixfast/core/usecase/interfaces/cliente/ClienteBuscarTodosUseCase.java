package br.com.postech.mixfast.core.usecase.interfaces.cliente;

import br.com.postech.mixfast.core.entity.Cliente;

import java.util.List;

public interface ClienteBuscarTodosUseCase {

    List<Cliente> buscarTodos();
}
