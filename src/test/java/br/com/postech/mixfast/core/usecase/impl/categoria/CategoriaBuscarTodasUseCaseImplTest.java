package br.com.postech.mixfast.core.usecase.impl.categoria;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.exception.categoria.CategoriaListEmptyException;
import br.com.postech.mixfast.core.gateway.CategoriaGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaBuscarTodasUseCaseImplTest {

    @InjectMocks
    private CategoriaBuscarTodasUseCaseImpl categoriaBuscarTodasUseCaseImpl;
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
    void deveBuscarTodasCategoriaComSucesso() {
        when(categoriaGateway.buscarTodas())
                .thenReturn(List.of(categoriaResponse));

        List<Categoria> listaCategorias = categoriaBuscarTodasUseCaseImpl.buscarTodas();

        Assertions.assertNotNull(listaCategorias);
        Assertions.assertEquals(categoriaRequest.getNome(), listaCategorias.get(0).getNome());
    }

    @Test
    void naoDeveBuscarTodasCategoria_Erro() {
        Exception exception = Assertions.assertThrows(CategoriaListEmptyException.class, () ->
                categoriaBuscarTodasUseCaseImpl.buscarTodas());

        String mensagemEsperada = "Lista de categorias em branco";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}