package br.com.postech.mixfast.dataproviders.gateway.database;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
import br.com.postech.mixfast.dataproviders.model.db.CategoriaDB;
import br.com.postech.mixfast.dataproviders.model.mapper.CategoriaDBMapper;
import br.com.postech.mixfast.dataproviders.repository.CategoriaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaGatewayImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();
    public static final String NOME_CATEGORIA = "Teste";

    @InjectMocks
    private CategoriaGatewayImpl categoriaGatewayImpl;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private CategoriaDBMapper categoriaDBMapper;

    private Categoria categoriaRequest;
    private Categoria categoriaResponse;
    private CategoriaDB categoriaDBRequest;
    private CategoriaDB categoriaDBResponse;

    @BeforeEach
    void setUp() {
        categoriaRequest = Categoria.builder()
                .nome("Categoria Teste")
                .build();

        categoriaResponse = Categoria.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Categoria Teste")
                .build();

        categoriaDBRequest = CategoriaDB.builder()
                .nome("Categoria Teste")
                .build();

        categoriaDBResponse = CategoriaDB.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Categoria Teste")
                .build();
    }

    @Test
    void cadastrarOuAtualizar_Sucesso() {
        when(categoriaDBMapper.entityToDB(any(Categoria.class)))
                .thenReturn(categoriaDBRequest);

        when(categoriaRepository.save(any(CategoriaDB.class)))
                .thenReturn(categoriaDBResponse);

        when(categoriaDBMapper.dbToEntity(any(CategoriaDB.class)))
                .thenReturn(categoriaResponse);

        Categoria categoriaCadastrada = categoriaGatewayImpl.cadastrarOuAtualizar(categoriaRequest);

        Assertions.assertNotNull(categoriaCadastrada);
        Assertions.assertEquals(categoriaRequest.getNome(), categoriaCadastrada.getNome());
    }

    @Test
    void cadastrarOuAtualizar_Erro() {
        when(categoriaDBMapper.entityToDB(any(Categoria.class)))
                .thenReturn(categoriaDBRequest);

        when(categoriaRepository.save(any(CategoriaDB.class)))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                categoriaGatewayImpl.cadastrarOuAtualizar(categoriaRequest));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarTodas_Sucesso() {
        when(categoriaRepository.findAll())
                .thenReturn(List.of(categoriaDBResponse));

        when(categoriaDBMapper.dbToEntity(any(CategoriaDB.class)))
                .thenReturn(categoriaResponse);

        List<Categoria> listaCategoriaEncontrada = categoriaGatewayImpl.buscarTodas();

        Assertions.assertNotNull(listaCategoriaEncontrada);
        Assertions.assertEquals(1, listaCategoriaEncontrada.size());
    }

    @Test
    void buscarTodas_Erro() {
        when(categoriaRepository.findAll())
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                categoriaGatewayImpl.buscarTodas());

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarPorCodigo_Sucesso() {
        when(categoriaRepository.findById(anyString()))
                .thenReturn(Optional.of(categoriaDBResponse));

        when(categoriaDBMapper.dbToEntity(any(CategoriaDB.class)))
                .thenReturn(categoriaResponse);

        Categoria categoriaEncontrada = categoriaGatewayImpl.buscarPorCodigo(CODIGO);

        Assertions.assertNotNull(categoriaEncontrada);
        Assertions.assertEquals(categoriaRequest.getNome(), categoriaEncontrada.getNome());
    }

    @Test
    void buscarPorCodigo_Erro() {
        when(categoriaRepository.findById(anyString()))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                categoriaGatewayImpl.buscarPorCodigo(CODIGO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void deletarPorCodigo_Sucesso() {
        doNothing().when(categoriaRepository)
                .deleteById(CODIGO);

        categoriaGatewayImpl.deletarPorCodigo(CODIGO);

        verify(categoriaRepository, times(1)).deleteById(CODIGO);
    }

    @Test
    void deletarPorCodigo_Erro() {
        doThrow(new ArithmeticException()).when(categoriaRepository)
                .deleteById(anyString());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                categoriaGatewayImpl.deletarPorCodigo(CODIGO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void encontrarPorNome_Sucesso() {
        when(categoriaRepository.existsByNome(anyString()))
                .thenReturn(Boolean.TRUE);

        Boolean categoriaNomeExistente = categoriaGatewayImpl.encontrarPorNome(NOME_CATEGORIA);

        Assertions.assertTrue(categoriaNomeExistente);
    }

    @Test
    void encontrarPorNome_Erro() {
        when(categoriaRepository.existsByNome(anyString()))
                .thenThrow(new NullPointerException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                categoriaGatewayImpl.encontrarPorNome(NOME_CATEGORIA));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}