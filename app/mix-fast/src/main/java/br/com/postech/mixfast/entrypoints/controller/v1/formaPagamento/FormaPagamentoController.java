package br.com.postech.mixfast.entrypoints.controller.v1.formaPagamento;

import br.com.postech.mixfast.core.entity.FormaPagamento;
import br.com.postech.mixfast.core.usecase.interfaces.formaPagamento.*;
import br.com.postech.mixfast.entrypoints.docs.FormaPagamentoDocumentable;
import br.com.postech.mixfast.entrypoints.http.FormaPagamentoHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.FormaPagamentoHttpMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/v1/formas_pagamento")
public class FormaPagamentoController implements FormaPagamentoDocumentable {

    private final FormaPagamentoHttpMapper formaPagamentoHttpMapper;
    private final FormaPagamentoCadastrarUseCase formaPagamentoCadastrarUseCase;
    private final FormaPagamentoBuscarTodasUseCase formaPagamentoBuscarTodasUseCase;
    private final FormaPagamentoBuscarPorCodigoUseCase formaPagamentoBuscarPorCodigoUseCase;
    private final FormaPagamentoAtualizarUseCase formaPagamaneotAtualizarUseCase;
    private final FormaPagamentoDeletarPorCodigoUseCase formaPagamentoDeletarPorCodigoUseCase;

    @PostMapping
    public ResponseEntity<FormaPagamentoHttp> cadastrar(@Valid @RequestBody FormaPagamentoHttp formaPagamentoHttp) {
        FormaPagamento formaPagamento = formaPagamentoCadastrarUseCase.cadastrar(formaPagamentoHttpMapper.httpToEntity(formaPagamentoHttp));
        log.info("Forma de pagamento cadastrada com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoHttpMapper.entityToHttp(formaPagamento));
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamentoHttp>> buscarTodas() {
        List<FormaPagamento> listaFormaPagamento = formaPagamentoBuscarTodasUseCase.buscarTodas();
        log.info("Lista de formas de pagamento preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoHttpMapper.entityListToHttpList(listaFormaPagamento));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<FormaPagamentoHttp> buscarPorCodigo(@PathVariable("codigo") String codigo) {
        FormaPagamento formaPagamento = formaPagamentoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        log.info("Forma de pagamento encontrada com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoHttpMapper.entityToHttp(formaPagamento));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<FormaPagamentoHttp> atualizar(@PathVariable("codigo") String codigo,
                                                        @Valid @RequestBody FormaPagamentoHttp formaPagamentoHttp) {
        FormaPagamento formaPagamento = formaPagamaneotAtualizarUseCase.atualizar(codigo, formaPagamentoHttpMapper.httpToEntity(formaPagamentoHttp));
        log.info("Forma de pagamento atualizada com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoHttpMapper.entityToHttp(formaPagamento));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarPorCodigo(@PathVariable("codigo") String codigo) {
        formaPagamentoDeletarPorCodigoUseCase.deletarPorCodigo(codigo);
        log.info("Forma de pagamento deletada com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
