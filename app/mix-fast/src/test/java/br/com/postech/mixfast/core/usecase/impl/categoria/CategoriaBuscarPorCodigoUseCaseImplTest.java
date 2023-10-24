package br.com.postech.mixfast.core.usecase.impl.categoria;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.exception.categoria.CategoriaNotFoundException;
import br.com.postech.mixfast.core.gateway.CategoriaGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaBuscarPorCodigoUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private CategoriaBuscarPorCodigoUseCaseImpl categoriaBuscarPorCodigoUseCaseImpl;
    @Mock
    private CategoriaGateway categoriaGateway;

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
    void deveBuscarPorCodigoCategoriaComSucesso() {
        when(categoriaGateway.buscarPorCodigo(anyString()))
                .thenReturn(categoriaResponse);

        Categoria categoriaEncontrada = categoriaBuscarPorCodigoUseCaseImpl.buscarPorCodigo(CODIGO);

        Assertions.assertNotNull(categoriaEncontrada);
        Assertions.assertEquals(categoriaRequest.getNome(), categoriaEncontrada.getNome());
    }

    @Test
    void naoDeveBuscarPorCodigoCategoria_Erro() {
        Exception exception = Assertions.assertThrows(CategoriaNotFoundException.class, () ->
                categoriaBuscarPorCodigoUseCaseImpl.buscarPorCodigo(CODIGO));

        String mensagemEsperada = "Categoria não encontrada com o código informado";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}