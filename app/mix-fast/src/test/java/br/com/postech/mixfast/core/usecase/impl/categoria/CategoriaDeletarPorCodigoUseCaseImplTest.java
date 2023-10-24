package br.com.postech.mixfast.core.usecase.impl.categoria;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.gateway.CategoriaGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaBuscarPorCodigoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaDeletarPorCodigoUseCaseImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private CategoriaDeletarPorCodigoUseCaseImpl categoriaDeletarPorCodigoUseCaseImpl;
    @Mock
    private CategoriaGateway categoriaGateway;
    @Mock
    private CategoriaBuscarPorCodigoUseCase categoriaBuscarPorCodigoUseCase;

    private Categoria categoriaResponse;

    @BeforeEach
    void setUp() {
        categoriaResponse = Categoria.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Categoria Teste")
                .build();
    }

    @Test
    void deveDeletarUmaCategoriaComSucesso() {
        when(categoriaBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
                .thenReturn(categoriaResponse);

        doNothing().when(categoriaGateway)
                .deletarPorCodigo(CODIGO);

        categoriaDeletarPorCodigoUseCaseImpl.deletarPorCodigo(CODIGO);

        verify(categoriaBuscarPorCodigoUseCase, times(1)).buscarPorCodigo(CODIGO);
        verify(categoriaGateway, times(1)).deletarPorCodigo(CODIGO);
    }
}