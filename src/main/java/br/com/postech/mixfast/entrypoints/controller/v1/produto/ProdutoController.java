package br.com.postech.mixfast.entrypoints.controller.v1.produto;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.usecase.interfaces.produto.*;
import br.com.postech.mixfast.entrypoints.http.ProdutoHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.ProdutoHttpMapper;
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
@RequestMapping(value = "/v1/produtos")
public class ProdutoController {

    private final ProdutoHttpMapper produtoHttpMapper;
    private final ProdutoCadastrarUseCase produtoCadastrarUseCase;
    private final ProdutoBuscarTodosUseCase produtoBuscarTodosUseCase;
    private final ProdutoBuscarPorCodigoUseCase produtoBuscarPorCodigoUseCase;
    private final ProdutoAtualizarUseCase produtoAtualizarUseCase;
    private final ProdutoDeletarPorCodigoUseCase produtoDeletarPorCodigoUseCase;
    private final ProdutoBuscarPorCategoriaUseCase produtoBuscarPorCategoriaUseCase;

    @Operation(summary = "Cadastrar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar um novo produto com os dados informados"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @PostMapping
    public ResponseEntity<ProdutoHttp> cadastrar(@Valid @RequestBody ProdutoHttp produtoHttp) {
        Produto produto = produtoCadastrarUseCase.cadastrar(produtoHttpMapper.httpToEntity(produtoHttp));
        log.info("Produto cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoHttpMapper.entityToHttp(produto));
    }

    @Operation(summary = "Buscar todos produtos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos preenchida com sucesso"),
            @ApiResponse(responseCode = "204", description = "Lista de produtos em branco"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @GetMapping
    public ResponseEntity<List<ProdutoHttp>> buscarTodos() {
        List<Produto> listaProdutos = produtoBuscarTodosUseCase.buscarTodos();
        List<ProdutoHttp> listaProdutosHttp = new ArrayList<>();

        listaProdutos.forEach(result -> {
            ProdutoHttp produtoHttp = produtoHttpMapper.entityToHttp(result);
            listaProdutosHttp.add(produtoHttp);
        });

        log.info("Lista de produtos preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(listaProdutosHttp);
    }

    @Operation(summary = "Buscar um produto cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @GetMapping("/{codigo}")
    public ResponseEntity<ProdutoHttp> buscarPorCodigo(@PathVariable("codigo") String codigo) {
        Produto produto = produtoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        log.info("Produto encontrado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(produtoHttpMapper.entityToHttp(produto));
    }

    @Operation(summary = "Atualizar um produto cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @PutMapping("/{codigo}")
    public ResponseEntity<ProdutoHttp> atualizar(@PathVariable("codigo") String codigo,
                                                 @Valid @RequestBody ProdutoHttp produtoHttp) {
        Produto produto = produtoAtualizarUseCase.atualizar(codigo, produtoHttpMapper.httpToEntity(produtoHttp));
        log.info("Produto atualizado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(produtoHttpMapper.entityToHttp(produto));
    }

    @Operation(summary = "Deletar um produto cadastrado por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarPorCodigo(@PathVariable("codigo") String codigo) {
        produtoDeletarPorCodigoUseCase.deletarPorCodigo(codigo);
        log.info("Produto deletado com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Buscar todos produtos por categoria cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos por categoria preenchida com sucesso"),
            @ApiResponse(responseCode = "204", description = "Lista de produtos por categoria em branco"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @GetMapping("/categoria/{categoria_codigo}")
    public ResponseEntity<List<ProdutoHttp>> buscarPorCategoria(@PathVariable("categoria_codigo") String categoria) {
        List<Produto> listaProdutos = produtoBuscarPorCategoriaUseCase.buscarPorCategoria(categoria);
        List<ProdutoHttp> listaProdutosHttp = new ArrayList<>();

        listaProdutos.forEach(result -> {
            ProdutoHttp produtoHttp = produtoHttpMapper.entityToHttp(result);
            listaProdutosHttp.add(produtoHttp);
        });

        log.info("Lista de produtos preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(listaProdutosHttp);
    }
}
