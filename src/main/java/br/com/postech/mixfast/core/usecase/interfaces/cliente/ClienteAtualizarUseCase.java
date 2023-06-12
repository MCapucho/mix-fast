package br.com.postech.mixfast.core.usecase.interfaces.cliente;

import br.com.postech.mixfast.core.entity.Cliente;

public interface ClienteAtualizarUseCase {

    Cliente atualizar(String codigo, Cliente cliente);
}
