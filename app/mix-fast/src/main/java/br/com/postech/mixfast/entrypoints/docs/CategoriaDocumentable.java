package br.com.postech.mixfast.entrypoints.docs;

import br.com.postech.mixfast.entrypoints.handler.ErrorResponse;
import br.com.postech.mixfast.entrypoints.http.CategoriaHttp;
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

@Tag(name = "Categorias")
public interface CategoriaDocumentable {

    @Operation(summary = "Cadastrar uma nova categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso",
                    content = { @Content(schema = @Schema(implementation = CategoriaHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar uma nova categoria com os dados informados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<CategoriaHttp> cadastrar(@Parameter CategoriaHttp categoriaHttp);

    @Operation(summary = "Buscar todas categorias cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias preenchida com sucesso",
                    content = { @Content(array = @ArraySchema(schema = @Schema(implementation = CategoriaHttp.class)),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "Lista de categorias em branco",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<List<CategoriaHttp>> buscarTodas();

    @Operation(summary = "Buscar uma categoria cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso",
                    content = { @Content(schema = @Schema(implementation = CategoriaHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<CategoriaHttp> buscarPorCodigo(@Parameter(name = "codigo", description = "Código da Categoria",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);

    @Operation(summary = "Atualizar uma categoria cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                    content = { @Content(schema = @Schema(implementation = CategoriaHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<CategoriaHttp> atualizar(@Parameter(name = "codigo", description = "Código da Categoria",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo, @Parameter CategoriaHttp categoriaHttp);

    @Operation(summary = "Deletar uma categoria cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso",
                    content = { @Content(schema = @Schema(),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<Void> deletarPorCodigo(@Parameter(name = "codigo", description = "Código da Categoria",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);
}
