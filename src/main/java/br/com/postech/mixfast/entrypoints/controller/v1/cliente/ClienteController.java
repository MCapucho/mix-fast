package br.com.postech.mixfast.entrypoints.controller.v1.cliente;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.usecase.interfaces.cliente.*;
import br.com.postech.mixfast.entrypoints.http.ClienteHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.ClienteHttpMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping(value = "v1/clientes")
public class ClienteController {

    private final ClienteHttpMapper clienteHttpMapper;
    private final ClienteCadastrarUseCase clienteCadastrarUseCase;
    private final ClienteBuscarTodosUseCase clienteBuscarTodosUseCase;
    private final ClienteBuscarPorCodigoUseCase clienteBuscarPorCodigoUseCase;
    private final ClienteAtualizarUseCase clienteAtualizarUseCase;
    private final ClienteDeletarPorCodigoUseCase clienteDeletarPorCodigoUseCase;
    private final ClienteBuscarPorCpfUseCase clienteBuscarPorCpfUseCase;

    @Operation(summary = "Cadastrar um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar um novo cliente com os dados informados"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @PostMapping
    public ResponseEntity<ClienteHttp> cadastrar(@Valid @RequestBody ClienteHttp clienteHttp) {
        Cliente cliente = clienteCadastrarUseCase.cadastrar(clienteHttpMapper.httpToEntity(clienteHttp));
        log.info("Cliente cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteHttpMapper.entityToHttp(cliente));
    }

    @Operation(summary = "Buscar todos clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes preenchida com sucesso"),
            @ApiResponse(responseCode = "204", description = "Lista de clientes em branco"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
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

    @Operation(summary = "Buscar um cliente cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @GetMapping("/{codigo}")
    public ResponseEntity<ClienteHttp> buscarPorCodigo(@PathVariable("codigo") String codigo) {
        Cliente cliente = clienteBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        log.info("Cliente encontrado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(clienteHttpMapper.entityToHttp(cliente));
    }

    @Operation(summary = "Atualizar um cliente cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @PutMapping("/{codigo}")
    public ResponseEntity<ClienteHttp> atualizar(@PathVariable("codigo") String codigo,
                                                 @Valid @RequestBody ClienteHttp clienteHttp) {
        Cliente cliente = clienteAtualizarUseCase.atualizar(codigo, clienteHttpMapper.httpToEntity(clienteHttp));
        log.info("Cliente atualizado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(clienteHttpMapper.entityToHttp(cliente));
    }

    @Operation(summary = "Deletar um cliente cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarPorCodigo(@PathVariable("codigo") String codigo) {
        clienteDeletarPorCodigoUseCase.deletarPorCodigo(codigo);
        log.info("Cliente deletado com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Buscar um cliente cadastrado por CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o CPF informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteHttp> buscarPorCpf(@PathVariable("cpf") String cpf) {
        Cliente cliente = clienteBuscarPorCpfUseCase.buscarPorCpf(cpf);
        log.info("Cliente encontrado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(clienteHttpMapper.entityToHttp(cliente));
    }
}
