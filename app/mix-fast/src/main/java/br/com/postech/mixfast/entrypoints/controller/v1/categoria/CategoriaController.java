package br.com.postech.mixfast.entrypoints.controller.v1.categoria;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.*;
import br.com.postech.mixfast.entrypoints.docs.CategoriaDocumentable;
import br.com.postech.mixfast.entrypoints.http.CategoriaHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.CategoriaHttpMapper;
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
@RequestMapping(value = "/v1/categorias")
public class CategoriaController implements CategoriaDocumentable {

    private final CategoriaHttpMapper categoriaHttpMapper;
    private final CategoriaCadastrarUseCase categoriaCadastrarUseCase;
    private final CategoriaBuscarTodasUseCase categoriaBuscarTodasUseCase;
    private final CategoriaBuscarPorCodigoUseCase categoriaBuscarPorCodigoUseCase;
    private final CategoriaAtualizarUseCase categoriaAtualizarUseCase;
    private final CategoriaDeletarPorCodigoUseCase categoriaDeletarPorCodigoUseCase;

    @PostMapping
    public ResponseEntity<CategoriaHttp> cadastrar(@Valid @RequestBody CategoriaHttp categoriaHttp) {
        Categoria categoria = categoriaCadastrarUseCase.cadastrar(categoriaHttpMapper.httpToEntity(categoriaHttp));
        log.info("Categoria cadastrada com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaHttpMapper.entityToHttp(categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaHttp>> buscarTodas() {
        List<Categoria> listaCategoria = categoriaBuscarTodasUseCase.buscarTodas();
        log.info("Lista de categorias preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(categoriaHttpMapper.entityListToHttpList(listaCategoria));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<CategoriaHttp> buscarPorCodigo(@PathVariable("codigo") String codigo) {
        Categoria categoria = categoriaBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        log.info("Categoria encontrada com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(categoriaHttpMapper.entityToHttp(categoria));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<CategoriaHttp> atualizar(@PathVariable("codigo") String codigo,
                                                   @Valid @RequestBody CategoriaHttp categoriaHttp) {
        Categoria categoria = categoriaAtualizarUseCase.atualizar(codigo, categoriaHttpMapper.httpToEntity(categoriaHttp));
        log.info("Categoria atualizada com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(categoriaHttpMapper.entityToHttp(categoria));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarPorCodigo(@PathVariable("codigo") String codigo) {
        categoriaDeletarPorCodigoUseCase.deletarPorCodigo(codigo);
        log.info("Categoria deletada com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
