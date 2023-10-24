package br.com.postech.mixfast.core.gateway;

import br.com.postech.mixfast.core.entity.Cliente;

import java.util.List;

public interface ClienteGateway {

    Cliente cadastrarOuAtualizar(Cliente cliente);
    List<Cliente> buscarTodos();
    Cliente buscarPorCodigo(String codigo);
    void deletarPorCodigo(String codigo);
    Cliente buscarPorCpf(String cpf);
    Boolean encontrarPorCpf(String cpf);
    Boolean encontrarPorEmail(String email);
}
