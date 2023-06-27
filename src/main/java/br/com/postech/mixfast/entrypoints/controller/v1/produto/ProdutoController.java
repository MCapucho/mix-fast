package br.com.postech.mixfast.entrypoints.controller.v1.produto;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.usecase.interfaces.produto.*;
import br.com.postech.mixfast.entrypoints.docs.ProdutoDocumentable;
import br.com.postech.mixfast.entrypoints.http.ProdutoHttp;
import br.com.postech.mixfast.entrypoints.http.interfaces.PostValidation;
import br.com.postech.mixfast.entrypoints.http.mapper.ProdutoHttpMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/v1/produtos")
public class ProdutoController implements ProdutoDocumentable {

    private final ProdutoHttpMapper produtoHttpMapper;
    private final ProdutoCadastrarUseCase produtoCadastrarUseCase;
    private final ProdutoBuscarTodosUseCase produtoBuscarTodosUseCase;
    private final ProdutoBuscarPorCodigoUseCase produtoBuscarPorCodigoUseCase;
    private final ProdutoAtualizarUseCase produtoAtualizarUseCase;
    private final ProdutoDeletarPorCodigoUseCase produtoDeletarPorCodigoUseCase;
    private final ProdutoBuscarPorCategoriaUseCase produtoBuscarPorCategoriaUseCase;

    @PostMapping
    public ResponseEntity<ProdutoHttp> cadastrar(@Validated(PostValidation.class) @RequestBody ProdutoHttp produtoHttp) {
        Produto produto = produtoCadastrarUseCase.cadastrar(produtoHttpMapper.httpToEntity(produtoHttp));
        log.info("Produto cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoHttpMapper.entityToHttp(produto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoHttp>> buscarTodos() {
        List<Produto> listaProdutos = produtoBuscarTodosUseCase.buscarTodos();
        log.info("Lista de produtos preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(produtoHttpMapper.entityListToHttpList(listaProdutos));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProdutoHttp> buscarPorCodigo(@PathVariable("codigo") String codigo) {
        Produto produto = produtoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        log.info("Produto encontrado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(produtoHttpMapper.entityToHttp(produto));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ProdutoHttp> atualizar(@PathVariable("codigo") String codigo,
                                                 @Validated(PutMapping.class) @RequestBody ProdutoHttp produtoHttp) {
        Produto produto = produtoAtualizarUseCase.atualizar(codigo, produtoHttpMapper.httpToEntity(produtoHttp));
        log.info("Produto atualizado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(produtoHttpMapper.entityToHttp(produto));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarPorCodigo(@PathVariable("codigo") String codigo) {
        produtoDeletarPorCodigoUseCase.deletarPorCodigo(codigo);
        log.info("Produto deletado com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/categoria/{categoriaCodigo}")
    public ResponseEntity<List<ProdutoHttp>> buscarPorCategoria(@PathVariable("categoriaCodigo") String categoria) {
        List<Produto> listaProdutos = produtoBuscarPorCategoriaUseCase.buscarPorCategoria(categoria);
        log.info("Lista de produtos preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(produtoHttpMapper.entityListToHttpList(listaProdutos));
    }
}
