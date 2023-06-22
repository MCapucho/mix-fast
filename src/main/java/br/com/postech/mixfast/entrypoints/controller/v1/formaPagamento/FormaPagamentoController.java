package br.com.postech.mixfast.entrypoints.controller.v1.formaPagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.*;
import br.com.postech.mixfast.entrypoints.handler.ErrorResponse;
import br.com.postech.mixfast.entrypoints.http.FormaPagamentoHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.FormaPagamentoHttpMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

@Tag(name = "Formas de Pagamento")
@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/v1/formas_pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoHttpMapper formaPagamentoHttpMapper;
    private final FormaPagamentoCadastrarUseCase formaPagamentoCadastrarUseCase;
    private final FormaPagamentoBuscarTodasUseCase formaPagamentoBuscarTodasUseCase;
    private final FormaPagamentoBuscarPorCodigoUseCase formaPagamentoBuscarPorCodigoUseCase;
    private final FormaPagamentoAtualizarUseCase formaPagamaneotAtualizarUseCase;
    private final FormaPagamentoDeletarPorCodigoUseCase formaPagamentoDeletarPorCodigoUseCase;

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
    @PostMapping
    public ResponseEntity<FormaPagamentoHttp> cadastrar(@Valid @RequestBody FormaPagamentoHttp formaPagamentoHttp) {
        FormaPagamento formaPagamento = formaPagamentoCadastrarUseCase.cadastrar(formaPagamentoHttpMapper.httpToEntity(formaPagamentoHttp));
        log.info("Forma de pagamento cadastrada com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoHttpMapper.entityToHttp(formaPagamento));
    }

    @Operation(summary = "Buscar todas formas de pagamento cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de formas de pagamento preenchida com sucesso"),
            @ApiResponse(responseCode = "204", description = "Lista de formas de pagamento em branco"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @GetMapping
    public ResponseEntity<List<FormaPagamentoHttp>> buscarTodas() {
        List<FormaPagamento> listaFormaPagamento = formaPagamentoBuscarTodasUseCase.buscarTodas();
        List<FormaPagamentoHttp> listaFormasPagamentoHttp = new ArrayList<>();

        listaFormaPagamento.forEach(result -> {
            FormaPagamentoHttp formaPagamentoHttp = formaPagamentoHttpMapper.entityToHttp(result);
            listaFormasPagamentoHttp.add(formaPagamentoHttp);
        });

        log.info("Lista de formas de pagamento preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(listaFormasPagamentoHttp);
    }

    @Operation(summary = "Buscar uma forma de pagamento cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forma de Pagamento encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Forma de Pagamento não encontrada com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @GetMapping("/{codigo}")
    public ResponseEntity<FormaPagamentoHttp> buscarPorCodigo(@PathVariable("codigo") String codigo) {
        FormaPagamento formaPagamento = formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        log.info("Forma de pagamento encontrada com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoHttpMapper.entityToHttp(formaPagamento));
    }

    @Operation(summary = "Atualizar uma forma de pagamento cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forma de Pagamento atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Forma de Pagamento não encontrada com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @PutMapping("/{codigo}")
    public ResponseEntity<FormaPagamentoHttp> atualizar(@PathVariable("codigo") String codigo,
                                                        @Valid @RequestBody FormaPagamentoHttp formaPagamentoHttp) {
        FormaPagamento formaPagamento = formaPagamaneotAtualizarUseCase.atualizar(codigo, formaPagamentoHttpMapper.httpToEntity(formaPagamentoHttp));
        log.info("Forma de pagamento atualizada com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoHttpMapper.entityToHttp(formaPagamento));
    }

    @Operation(summary = "Deletar uma forma de pagamento cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forma de Pagamento deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Forma de Pagamento não encontrada com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarPorCodigo(@PathVariable("codigo") String codigo) {
        formaPagamentoDeletarPorCodigoUseCase.deletarPorCodigo(codigo);
        log.info("Forma de pagamento deletada com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
