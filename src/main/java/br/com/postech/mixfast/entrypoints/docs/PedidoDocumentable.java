package br.com.postech.mixfast.entrypoints.docs;

import br.com.postech.mixfast.entrypoints.http.PedidoHttp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Pedidos")
public interface PedidoDocumentable {

    @Operation(summary = "Emitir um novo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido emitido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao emitir o pedido informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    ResponseEntity<PedidoHttp> emitir(@Parameter PedidoHttp pedidoHttp);

    @Operation(summary = "Buscar todos pedidos emitidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos preenchida com sucesso"),
            @ApiResponse(responseCode = "204", description = "Lista de produtos em branco"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    ResponseEntity<List<PedidoHttp>> buscarTodos();

    @Operation(summary = "Buscar um pedido emitido por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    ResponseEntity<PedidoHttp> buscarPorCodigo(@Parameter(name = "codigo", description = "Código do Pedido",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);

    @Operation(summary = "Atualizar status do pedido em preparação por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido atualizado em preparamento com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status do pedido não pode ser alterado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    ResponseEntity<Void> preparar(@Parameter(name = "codigo", description = "Código do Pedido",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);

    @Operation(summary = "Atualizar status do pedido para entregue por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido atualizado para entregue com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status do pedido não pode ser alterado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    ResponseEntity<Void> entregar(@Parameter(name = "codigo", description = "Código do Pedido",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);

    @Operation(summary = "Atualizar status do pedido para finalizado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido atualizado para finalizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status do pedido não pode ser alterado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    ResponseEntity<Void> finalizar(@Parameter(name = "codigo", description = "Código do Pedido",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);

    @Operation(summary = "Atualizar status do pedido para cancelado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido atualizado para cancelado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status do pedido não pode ser alterado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    ResponseEntity<Void> cancelar(@Parameter(name = "codigo", description = "Código do Pedido",
            example = "77b36beb-68cd-4939-9911-fe92a79cff99") String codigo);
}
