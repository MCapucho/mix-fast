package br.com.postech.mixfast.entrypoints.docs;

import br.com.postech.mixfast.entrypoints.handler.ErrorResponse;
import br.com.postech.mixfast.entrypoints.http.CategoriaHttp;
import br.com.postech.mixfast.entrypoints.http.ProdutoHttp;
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

@Tag(name = "Produtos")
public interface ProdutoDocumentable {

    @Operation(summary = "Cadastrar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso",
                    content = { @Content(schema = @Schema(implementation = ProdutoHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar um novo produto com os dados informados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<ProdutoHttp> cadastrar(@Parameter ProdutoHttp produtoHttp);

    @Operation(summary = "Buscar todos produtos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos preenchida com sucesso",
                    content = { @Content(array = @ArraySchema(schema = @Schema(implementation = CategoriaHttp.class)),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "Lista de produtos em branco",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<List<ProdutoHttp>> buscarTodos();

    @Operation(summary = "Buscar um produto cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso",
                    content = { @Content(schema = @Schema(implementation = ProdutoHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<ProdutoHttp> buscarPorCodigo(@Parameter(name = "codigo", description = "Código do Produto",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);

    @Operation(summary = "Atualizar um produto cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                    content = { @Content(schema = @Schema(implementation = ProdutoHttp.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<ProdutoHttp> atualizar(@Parameter(name = "codigo", description = "Código do Produto",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo, @Parameter ProdutoHttp produtoHttp);

    @Operation(summary = "Deletar um produto cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso",
                    content = { @Content(schema = @Schema(),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado com o código informado",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<Void> deletarPorCodigo(@Parameter(name = "codigo", description = "Código do Produto",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);

    @Operation(summary = "Buscar todos produtos por categoria cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos por categoria preenchida com sucesso",
                    content = { @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoHttp.class)),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "Lista de produtos por categoria em branco",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<List<ProdutoHttp>> buscarPorCategoria(@Parameter(name = "categoriaCodigo", description = "Código da Categoria",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String categoria);
}
