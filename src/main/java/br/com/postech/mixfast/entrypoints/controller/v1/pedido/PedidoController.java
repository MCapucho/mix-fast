package br.com.postech.mixfast.entrypoints.controller.v1.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoAtualizarStatusUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoBuscarPorCodigoUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoBuscarTodosUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoEmitirUseCase;
import br.com.postech.mixfast.entrypoints.http.PedidoHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.PedidoHttpMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Pedidos")
@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/v1/pedidos")
public class PedidoController {

    private final PedidoHttpMapper pedidoHttpMapper;
    private final PedidoEmitirUseCase pedidoEmitirUseCase;
    private final PedidoBuscarTodosUseCase pedidoBuscarTodosUseCase;
    private final PedidoBuscarPorCodigoUseCase pedidoBuscarPorCodigoUseCase;
    private final PedidoAtualizarStatusUseCase pedidoAtualizarStatusUseCase;

    @Operation(summary = "Emitir um novo pedido")
    @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Pedido emitido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao emitir o pedido informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @PostMapping
    public ResponseEntity<PedidoHttp> emitir(@Valid @RequestBody PedidoHttp pedidoHttp) {
        Pedido pedido = pedidoEmitirUseCase.emitir(pedidoHttpMapper.httpToEntity(pedidoHttp));
        PedidoHttp pedidoHttpEmitido = pedidoHttpMapper.entityToHttp(pedido);

        pedidoHttpEmitido.add(linkTo(methodOn(PedidoController.class)
                .preparar(pedidoHttpEmitido.getCodigo())).withSelfRel());
        pedidoHttpEmitido.add(linkTo(methodOn(PedidoController.class)
                .entregar(pedidoHttpEmitido.getCodigo())).withSelfRel());
        pedidoHttpEmitido.add(linkTo(methodOn(PedidoController.class)
                .finalizar(pedidoHttpEmitido.getCodigo())).withSelfRel());
        pedidoHttpEmitido.add(linkTo(methodOn(PedidoController.class)
                .cancelar(pedidoHttpEmitido.getCodigo())).withSelfRel());

        log.info("Pedido emitido com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoHttpEmitido);
    }

    @Operation(summary = "Buscar todos pedidos emitidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos preenchida com sucesso"),
            @ApiResponse(responseCode = "204", description = "Lista de produtos em branco"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @GetMapping
    public ResponseEntity<List<PedidoHttp>> buscarTodos() {
        List<Pedido> listaPedidos = pedidoBuscarTodosUseCase.buscarTodos();
        List<PedidoHttp> listaPedidosHttp = new ArrayList<>();

        listaPedidos.forEach(result -> {
            PedidoHttp pedidoHttp = pedidoHttpMapper.entityToHttp(result);
            listaPedidosHttp.add(pedidoHttp);
        });

        log.info("Lista de pedidos preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(listaPedidosHttp);
    }

    @Operation(summary = "Buscar um pedido emitido por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @GetMapping("/{codigo}")
    public ResponseEntity<PedidoHttp> buscarPorCodigo(@PathVariable("codigo") String codigo) {
        Pedido pedido = pedidoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        log.info("Pedido encontrado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(pedidoHttpMapper.entityToHttp(pedido));
    }

    @Operation(summary = "Atualizar status do pedido em preparação por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido atualizado em preparamento com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status do pedido não pode ser alterado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @PutMapping("/{codigo}/preparamento")
    public ResponseEntity<Void> preparar(@PathVariable("codigo") String codigo) {
        pedidoAtualizarStatusUseCase.preparar(codigo);
        log.info("Pedido atualizado em preparamento com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualizar status do pedido para entregue por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido atualizado para entregue com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status do pedido não pode ser alterado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @PutMapping("/{codigo}/entrega")
    public ResponseEntity<Void> entregar(@PathVariable("codigo") String codigo) {
        pedidoAtualizarStatusUseCase.entregar(codigo);
        log.info("Pedido atualizado em entregue com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualizar status do pedido para finalizado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido atualizado para finalizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status do pedido não pode ser alterado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @PutMapping("/{codigo}/finalizado")
    public ResponseEntity<Void> finalizar(@PathVariable("codigo") String codigo) {
        pedidoAtualizarStatusUseCase.finalizar(codigo);
        log.info("Pedido atualizado em finalizado com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualizar status do pedido para cancelado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido atualizado para cancelado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status do pedido não pode ser alterado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @PutMapping("/{codigo}/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable("codigo") String codigo) {
        pedidoAtualizarStatusUseCase.cancelar(codigo);
        log.info("Pedido atualizado em cancelado com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
