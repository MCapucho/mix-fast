package br.com.postech.mixfast.entrypoints.controller.cliente.v1;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.*;
import br.com.postech.mixfast.entrypoints.http.ClienteHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.ClienteHttpMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/clientes/v1")
public class ClienteController {

    private final ClienteHttpMapper clienteHttpMapper;
    private final ClienteCadastrarUseCase clienteCadastrarUseCase;
    private final ClienteBuscarTodosUseCase clienteBuscarTodosUseCase;
    private final ClienteBuscarPorCodigoUseCase clienteBuscarPorCodigoUseCase;
    private final ClienteAtualizarUseCase clienteAtualizarUseCase;
    private final ClienteDeletarPorCodigoUseCase clienteDeletarPorCodigoUseCase;
    private final ClienteBuscarPorCpfUseCase clienteBuscarPorCpfUseCase;

    @PostMapping
    public ResponseEntity<ClienteHttp> cadastrar(@Valid @RequestBody ClienteHttp clienteHttp) {
        Cliente cliente = clienteCadastrarUseCase.cadastrar(clienteHttpMapper.httpToEntity(clienteHttp));
        log.info("Cliente cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteHttpMapper.entityToHttp(cliente));
    }

    @GetMapping
    public ResponseEntity<List<ClienteHttp>> buscarTodos() {
        List<Cliente> listaClientes = clienteBuscarTodosUseCase.buscarTodos();
        List<ClienteHttp> listaClientesHttp = new ArrayList<>();

        listaClientes.forEach(result -> {
            ClienteHttp clienteHttp = clienteHttpMapper.entityToHttp(result);
            listaClientesHttp.add(clienteHttp);
        });

        log.info("Lista de cliente preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(listaClientesHttp);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ClienteHttp> buscarPorCodigo(@PathVariable("codigo") String codigo) {
        Cliente cliente = clienteBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        log.info("Cliente encontrado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(clienteHttpMapper.entityToHttp(cliente));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ClienteHttp> atualizar(@PathVariable("codigo") String codigo,
                                                 @Valid @RequestBody ClienteHttp clienteHttp) {
        Cliente cliente = clienteAtualizarUseCase.atualizar(codigo, clienteHttpMapper.httpToEntity(clienteHttp));
        log.info("Cliente atualizado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(clienteHttpMapper.entityToHttp(cliente));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarPorCodigo(@PathVariable("codigo") String codigo) {
        clienteDeletarPorCodigoUseCase.deletarPorCodigo(codigo);
        log.info("Cliente deletado com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteHttp> buscarPorCpf(@PathVariable("cpf") String cpf) {
        Cliente cliente = clienteBuscarPorCpfUseCase.buscarPorCpf(cpf);
        log.info("Cliente encontrado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(clienteHttpMapper.entityToHttp(cliente));
    }
}
