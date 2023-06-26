package br.com.postech.mixfast.entrypoints.docs;

import br.com.postech.mixfast.entrypoints.handler.ErrorResponse;
import br.com.postech.mixfast.entrypoints.http.ClienteHttp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Clientes")
public interface ClienteDocumentable {

    @Operation(summary = "Cadastrar um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso",
                    content = { @Content(schema = @Schema(implementation = ClienteHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar um novo cliente com os dados informados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<ClienteHttp> cadastrar(@Parameter ClienteHttp clienteHttp);

    @Operation(summary = "Buscar todos clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes preenchida com sucesso",
                    content = { @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteHttp.class)),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "Lista de clientes em branco",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<List<ClienteHttp>> buscarTodos();

    @Operation(summary = "Buscar um cliente cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso",
                    content = { @Content(schema = @Schema(implementation = ClienteHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<ClienteHttp> buscarPorCodigo(@Parameter(name = "codigo", description = "Código do Cliente",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);

    @Operation(summary = "Atualizar um cliente cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
                    content = { @Content(schema = @Schema(implementation = ClienteHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<ClienteHttp> atualizar(@Parameter(name = "codigo", description = "Código do Cliente",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo, @Parameter ClienteHttp clienteHttp);

    @Operation(summary = "Deletar um cliente cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso",
                    content = { @Content(schema = @Schema(),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<Void> deletarPorCodigo(@Parameter(name = "codigo", description = "Código do Cliente",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);

    @Operation(summary = "Buscar um cliente cadastrado por CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso",
                    content = { @Content(schema = @Schema(implementation = ClienteHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o CPF informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<ClienteHttp> buscarPorCpf(@Parameter(name = "cpf", description = "CPF do Cliente",
            example = "12345678900") String cpf);
}
