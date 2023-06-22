package br.com.postech.mixfast.entrypoints.controller.v1.categoria;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.*;
import br.com.postech.mixfast.entrypoints.handler.ErrorResponse;
import br.com.postech.mixfast.entrypoints.http.CategoriaHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.CategoriaHttpMapper;
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

@Tag(name = "Categorias")
@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/v1/categorias")
public class CategoriaController {

    private final CategoriaHttpMapper categoriaHttpMapper;
    private final CategoriaCadastrarUseCase categoriaCadastrarUseCase;
    private final CategoriaBuscarTodasUseCase categoriaBuscarTodasUseCase;
    private final CategoriaBuscarPorCodigoUseCase categoriaBuscarPorCodigoUseCase;
    private final CategoriaAtualizarUseCase categoriaAtualizarUseCase;
    private final CategoriaDeletarPorCodigoUseCase categoriaDeletarPorCodigoUseCase;

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
    @PostMapping
    public ResponseEntity<CategoriaHttp> cadastrar(@Valid @RequestBody CategoriaHttp categoriaHttp) {
        Categoria categoria = categoriaCadastrarUseCase.cadastrar(categoriaHttpMapper.httpToEntity(categoriaHttp));
        log.info("Categoria cadastrada com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaHttpMapper.entityToHttp(categoria));
    }

    @Operation(summary = "Buscar todas categorias cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias preenchida com sucesso"),
            @ApiResponse(responseCode = "204", description = "Lista de categorias em branco"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @GetMapping
    public ResponseEntity<List<CategoriaHttp>> buscarTodas() {
        List<Categoria> listaCategoria = categoriaBuscarTodasUseCase.buscarTodas();
        List<CategoriaHttp> listaCategoriasHttp = new ArrayList<>();

        listaCategoria.forEach(result -> {
            CategoriaHttp categoriaHttp = categoriaHttpMapper.entityToHttp(result);
            listaCategoriasHttp.add(categoriaHttp);
        });

        log.info("Lista de categorias preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(listaCategoriasHttp);
    }

    @Operation(summary = "Buscar uma categoria cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @GetMapping("/{codigo}")
    public ResponseEntity<CategoriaHttp> buscarPorCodigo(@PathVariable("codigo") String codigo) {
        Categoria categoria = categoriaBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        log.info("Categoria encontrada com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(categoriaHttpMapper.entityToHttp(categoria));
    }

    @Operation(summary = "Atualizar uma categoria cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @PutMapping("/{codigo}")
    public ResponseEntity<CategoriaHttp> atualizar(@PathVariable("codigo") String codigo,
                                                   @Valid @RequestBody CategoriaHttp categoriaHttp) {
        Categoria categoria = categoriaAtualizarUseCase.atualizar(codigo, categoriaHttpMapper.httpToEntity(categoriaHttp));
        log.info("Categoria atualizada com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(categoriaHttpMapper.entityToHttp(categoria));
    }

    @Operation(summary = "Deletar uma categoria cadastrada por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada com o código informado"),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados")})
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarPorCodigo(@PathVariable("codigo") String codigo) {
        categoriaDeletarPorCodigoUseCase.deletarPorCodigo(codigo);
        log.info("Categoria deletada com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
