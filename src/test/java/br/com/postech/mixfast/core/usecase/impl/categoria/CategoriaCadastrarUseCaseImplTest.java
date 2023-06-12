package br.com.postech.mixfast.core.usecase.impl.categoria;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.exception.categoria.CategoriaBadRequestException;
import br.com.postech.mixfast.core.gateway.CategoriaGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaCadastrarUseCaseImplTest {

    @InjectMocks
    private CategoriaCadastrarUseCaseImpl categoriaCadastrarUseCaseImpl;
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
    void deveCadastrarUmaCategoriaComSucesso() {
        when(categoriaGateway.cadastrarOuAtualizar(any()))
                .thenReturn(categoriaResponse);

        Categoria categoriaCadastrada = categoriaCadastrarUseCaseImpl.cadastrar(categoriaRequest);

        Assertions.assertNotNull(categoriaCadastrada);
        Assertions.assertEquals(categoriaRequest.getNome(), categoriaCadastrada.getNome());
    }

    @Test
    void naoDeveCadastrarUmaCategoria_Erro() {
        Exception exception = Assertions.assertThrows(CategoriaBadRequestException.class, () -> {
           categoriaCadastrarUseCaseImpl.cadastrar(categoriaRequest);
        });

        String mensagemEsperada = "Cadastro de categoria não foi concluído";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}