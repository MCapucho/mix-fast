package br.com.postech.mixfast.entrypoints.docs;

import br.com.postech.mixfast.entrypoints.handler.ErrorResponse;
import br.com.postech.mixfast.entrypoints.http.FormaPagamentoHttp;
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

@Tag(name = "Formas de Pagamento")
public interface FormaPagamentoDocumentable {

    @Operation(summary = "Cadastrar uma nova forma de pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Forma de Pagamento cadastrada com sucesso",
                    content = { @Content(schema = @Schema(implementation = FormaPagamentoHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar uma nova forma de pagamento com os dados informados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<FormaPagamentoHttp> cadastrar(@Parameter FormaPagamentoHttp formaPagamentoHttp);

    @Operation(summary = "Buscar todas formas de pagamento cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de formas de pagamento preenchida com sucesso",
                    content = { @Content(array = @ArraySchema(schema = @Schema(implementation = FormaPagamentoHttp.class)),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "Lista de formas de pagamento em branco",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<List<FormaPagamentoHttp>> buscarTodas();

    @Operation(summary = "Buscar uma forma de pagamento cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forma de Pagamento encontrada com sucesso",
                    content = { @Content(schema = @Schema(implementation = FormaPagamentoHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Forma de Pagamento não encontrada com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<FormaPagamentoHttp> buscarPorCodigo(@Parameter(name = "codigo", description = "Código da Forma de Pagamento",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);

    @Operation(summary = "Atualizar uma forma de pagamento cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forma de Pagamento atualizada com sucesso",
                    content = { @Content(schema = @Schema(implementation = FormaPagamentoHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Forma de Pagamento não encontrada com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<FormaPagamentoHttp> atualizar(@Parameter(name = "codigo", description = "Código da Forma de Pagamento",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo, @Parameter FormaPagamentoHttp formaPagamentoHttp);

    @Operation(summary = "Deletar uma forma de pagamento cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Forma de Pagamento deletada com sucesso",
                    content = { @Content(schema = @Schema(),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Forma de Pagamento não encontrada com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<Void> deletarPorCodigo(@Parameter(name = "codigo", description = "Código da Forma de Pagamento",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);
}
