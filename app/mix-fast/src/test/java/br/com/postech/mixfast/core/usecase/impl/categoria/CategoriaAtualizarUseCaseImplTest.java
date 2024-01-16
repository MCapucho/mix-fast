package br.com.postech.mixfast.core.usecase.impl.categoria;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.exception.categoria.CategoriaDuplicatedException;
import br.com.postech.mixfast.core.gateway.CategoriaGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaBuscarPorCodigoUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaAtualizarUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private CategoriaAtualizarUseCaseImpl categoriaAtualizarUseCaseImpl;
    @Mock
    private CategoriaGateway categoriaGateway;
    @Mock
    private CategoriaBuscarPorCodigoUseCase categoriaBuscarPorCodigoUseCase;

    private Categoria categoriaRequest;
    private Categoria categoriaResponse;

    @BeforeEach
    void setUp() {
        categoriaRequest = Categoria.builder()
                .nome("Categoria Teste")
                .build();

        categoriaResponse = Categoria.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Categoria Teste")
                .build();
    }

    @Test
    void deveAtualizarUmaCategoriaComSucesso() {
        when(categoriaBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(categoriaResponse);

        when(categoriaGateway.cadastrarOuAtualizar(any()))
                .thenReturn(categoriaResponse);

        Categoria categoriaAtualizada = categoriaAtualizarUseCaseImpl.atualizar(CODIGO, categoriaRequest);

        Assertions.assertNotNull(categoriaAtualizada);
        Assertions.assertEquals(categoriaRequest.getNome(), categoriaAtualizada.getNome());
    }

    @Test
    void deveAtualizarUmaCategoriaComSucesso_NomeNull() {
        categoriaRequest.setNome(null);

        when(categoriaBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(categoriaResponse);

        when(categoriaGateway.cadastrarOuAtualizar(any()))
                .thenReturn(categoriaResponse);

        Categoria categoriaAtualizada = categoriaAtualizarUseCaseImpl.atualizar(CODIGO, categoriaRequest);

        Assertions.assertNotNull(categoriaAtualizada);
    }

    @Test
    void naoDeveAtualizarUmaCategoria_Erro_CategoriaExistente() {
        when(categoriaGateway.encontrarPorNome(anyString()))
                .thenReturn(Boolean.TRUE);

        Exception exception = Assertions.assertThrows(CategoriaDuplicatedException.class, () ->
                categoriaAtualizarUseCaseImpl.atualizar(CODIGO, categoriaRequest));

        String mensagemEsperada = "Categoria informada já cadastrada";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}