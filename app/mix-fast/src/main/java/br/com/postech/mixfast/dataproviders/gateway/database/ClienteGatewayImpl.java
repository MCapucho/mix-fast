package br.com.postech.mixfast.dataproviders.gateway.database;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.gateway.ClienteGateway;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
import br.com.postech.mixfast.dataproviders.model.db.ClienteDB;
import br.com.postech.mixfast.dataproviders.model.mapper.ClienteDBMapper;
import br.com.postech.mixfast.dataproviders.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class ClienteGatewayImpl implements ClienteGateway {

    private static final String BANCO_DE_DADOS = "Erro na comunicação com o banco de dados";

    private final ClienteDBMapper clienteDBMapper;
    private final ClienteRepository clienteRepository;

    @Override
    public Cliente cadastrarOuAtualizar(Cliente cliente) {
        try {
            cliente.setCpf(cliente.getCpf().replace("-", "").replace(".", ""));
            ClienteDB clienteDB = clienteRepository.save(clienteDBMapper.entityToDB(cliente));
            return clienteDBMapper.dbToEntity(clienteDB);
        } catch (Exception e) {
            log.error("Erro ao cadastrar/atualizar um cliente", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Transactional
    @Override
    public List<Cliente> buscarTodos() {
        try {
            List<ClienteDB> listaClientesDB = clienteRepository.findAll();
            List<Cliente> listaClientes = new ArrayList<>();

            listaClientesDB.forEach(result -> {
                Cliente cliente = clienteDBMapper.dbToEntity(result);
                listaClientes.add(cliente);
            });

            return listaClientes;
        } catch (Exception e) {
            log.error("Erro ao buscar todos clientes", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Override
    public Cliente buscarPorCodigo(String codigo) {
        try {
            return clienteRepository.findById(codigo)
                    .stream()
                    .map(clienteDBMapper::dbToEntity)
                    .findAny()
                    .orElse(null);
        } catch (Exception e) {
            log.error("Erro ao buscar um cliente por código", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Override
    public void deletarPorCodigo(String codigo) {
        try {
            clienteRepository.deleteById(codigo);
        } catch (Exception e) {
            log.error("Erro ao deletar um cliente por código", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        try {
            ClienteDB clienteDB = clienteRepository.findByCpf(cpf);
            return clienteDBMapper.dbToEntity(clienteDB);
        } catch (Exception e) {
            log.error("Erro ao buscar um cliente por CPF", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Override
    public Boolean encontrarPorCpf(String cpf) {
        try {
            return clienteRepository.existsByCpf(cpf);
        } catch (Exception e) {
            log.error("Erro ao encontrar um cliente por CPF", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Override
    public Boolean encontrarPorEmail(String email) {
        try {
            return clienteRepository.existsByEmail(email);
        } catch (Exception e) {
            log.error("Erro ao encontrar um cliente por E-mail", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }
}
