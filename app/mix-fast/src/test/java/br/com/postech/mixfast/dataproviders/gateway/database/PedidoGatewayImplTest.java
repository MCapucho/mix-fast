package br.com.postech.mixfast.dataproviders.gateway.database;

import br.com.postech.mixfast.core.entity.Cliente;
import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.entity.PedidoProduto;
import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.entity.enums.StatusPagamento;
import br.com.postech.mixfast.core.entity.enums.StatusPedido;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
import br.com.postech.mixfast.dataproviders.model.db.*;
import br.com.postech.mixfast.dataproviders.model.mapper.PedidoDBMapper;
import br.com.postech.mixfast.dataproviders.repository.PedidoRepository;
import br.com.postech.mixfast.dataproviders.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoGatewayImplTest {

    public static final String CODIGO = UUID.randomUUID().toString();

    @InjectMocks
    private PedidoGatewayImpl pedidoGatewayImpl;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private PedidoDBMapper pedidoDBMapper;
    @Mock
    private ProdutoRepository produtoRepository;

    private Pedido pedidoRequest;
    private Pedido pedidoResponse;
    private PedidoDB pedidoDBResponse;
    private ProdutoDB produtoDB;

    @BeforeEach
    void setUp() {
        pedidoRequest = Pedido.builder()
                .cliente(Cliente.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .formaPagamento(UUID.randomUUID().toString())
                .itens(List.of(PedidoProduto.builder()
                        .produto(Produto.builder()
                                .codigo(UUID.randomUUID().toString())
                                .preco(new BigDecimal("10.00"))
                                .build())
                        .quantidade(1)
                        .build()))
                .statusPedido(StatusPedido.RECEBIDO)
                .statusPagamento(StatusPagamento.AGUARDANDO)
                .build();

        pedidoResponse = Pedido.builder()
                .codigo(UUID.randomUUID().toString())
                .cliente(Cliente.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .formaPagamento(UUID.randomUUID().toString())
                .itens(List.of(PedidoProduto.builder()
                        .produto(Produto.builder()
                                .codigo(UUID.randomUUID().toString())
                                .build())
                        .quantidade(1)
                        .build()))
                .fila(1)
                .valorTotal(new BigDecimal("10.00"))
                .qrCode("abc123")
                .statusPagamento(StatusPagamento.APROVADO)
                .build();

        pedidoDBResponse = PedidoDB.builder()
                .codigo(UUID.randomUUID().toString())
                .cliente(ClienteDB.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .formaPagamento(UUID.randomUUID().toString())
                .itens(List.of(PedidoProdutoDB.builder()
                        .produto(ProdutoDB.builder()
                                .codigo(UUID.randomUUID().toString())
                                .build())
                        .quantidade(1)
                        .build()))
                .statusPagamento(StatusPagamento.APROVADO.name())
                .statusPedido(StatusPedido.RECEBIDO.name())
                .build();

        produtoDB = ProdutoDB.builder()
                .codigo(UUID.randomUUID().toString())
                .nome("Produto Teste")
                .preco(new BigDecimal("10.00"))
                .categoria(CategoriaDB.builder()
                        .codigo(UUID.randomUUID().toString())
                        .build())
                .build();
    }

    @Test
    void emitir_Sucesso() {
        when(pedidoDBMapper.entityToDB(any(Pedido.class)))
                .thenReturn(pedidoDBResponse);

        when(produtoRepository.findById(anyString()))
                .thenReturn(Optional.of(produtoDB));

        when(pedidoRepository.recuperarNumeroFila())
                .thenReturn(null);

        when(pedidoRepository.save(any(PedidoDB.class)))
                .thenReturn(pedidoDBResponse);

        when(pedidoDBMapper.dbToEntity(any(PedidoDB.class)))
                .thenReturn(pedidoResponse);

        Pedido pedidoEmitido = pedidoGatewayImpl.emitir(pedidoRequest);

        Assertions.assertNotNull(pedidoEmitido);
        Assertions.assertEquals(1, pedidoEmitido.getFila());
        Assertions.assertEquals(new BigDecimal("10.00"), pedidoEmitido.getValorTotal());
    }

    @Test
    void emitir_Erro() {
        when(pedidoDBMapper.entityToDB(any(Pedido.class)))
                .thenReturn(pedidoDBResponse);

        when(produtoRepository.findById(anyString()))
                .thenReturn(Optional.of(produtoDB));

        when(pedidoRepository.recuperarNumeroFila())
                .thenReturn(1);

        when(pedidoRepository.save(any(PedidoDB.class)))
                .thenThrow(new NullPointerException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                pedidoGatewayImpl.emitir(pedidoRequest));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarTodos_Sucesso() {
        when(pedidoRepository.findAll(any(Sort.class)))
                .thenReturn(List.of(pedidoDBResponse));

        when(pedidoDBMapper.dbToEntity(any(PedidoDB.class)))
                .thenReturn(pedidoResponse);

        List<Pedido> listaPedidoEncontrado = pedidoGatewayImpl.buscarTodos();

        Assertions.assertNotNull(listaPedidoEncontrado);
        Assertions.assertEquals(1, listaPedidoEncontrado.size());
    }

    @Test
    void buscarTodos_Erro() {
        when(pedidoRepository.findAll())
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                pedidoGatewayImpl.buscarTodos());

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarPorCodigo_Sucesso() {
        when(pedidoRepository.findByCodigo(anyString()))
                .thenReturn(Optional.of(pedidoDBResponse));

        when(pedidoDBMapper.dbToEntity(any(PedidoDB.class)))
                .thenReturn(pedidoResponse);

        Pedido pedidoEncontrado = pedidoGatewayImpl.buscarPorCodigo(CODIGO);

        Assertions.assertNotNull(pedidoEncontrado);
        Assertions.assertEquals(1, pedidoEncontrado.getFila());
    }

    @Test
    void buscarPorCodigo_Erro() {
        when(pedidoRepository.findByCodigo(anyString()))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                pedidoGatewayImpl.buscarPorCodigo(CODIGO));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void atualizarStatusPedido_Sucesso() {
        pedidoRequest.setCodigo(UUID.randomUUID().toString());

        doNothing().when(pedidoRepository)
                .atualizarStatusPedido(anyString(), anyString());

        pedidoGatewayImpl.atualizarStatusPedido(pedidoRequest);

        verify(pedidoRepository, times(1))
                .atualizarStatusPedido(StatusPedido.RECEBIDO.name(), pedidoRequest.getCodigo());
    }

    @Test
    void atualizarStatusPedido_Erro() {
        pedidoRequest.setCodigo(UUID.randomUUID().toString());

        doThrow(new NullPointerException()).when(pedidoRepository)
                .atualizarStatusPedido(anyString(), anyString());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                pedidoGatewayImpl.atualizarStatusPedido(pedidoRequest));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void atualizarStatusPagamento_Sucesso() {
        pedidoRequest.setCodigo(UUID.randomUUID().toString());

        doNothing().when(pedidoRepository)
                .atualizarStatusPagamento(anyString(), anyString());

        pedidoGatewayImpl.atualizarStatusPagamento(pedidoRequest);

        verify(pedidoRepository, times(1))
                .atualizarStatusPagamento(StatusPagamento.AGUARDANDO.name(), pedidoRequest.getCodigo());
    }

    @Test
    void atualizarStatusPagamento_Erro() {
        pedidoRequest.setCodigo(UUID.randomUUID().toString());

        doThrow(new NullPointerException()).when(pedidoRepository)
                .atualizarStatusPagamento(anyString(), anyString());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                pedidoGatewayImpl.atualizarStatusPagamento(pedidoRequest));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void buscarPorStatusPedido_Sucesso() {
        when(pedidoRepository.findByStatusPedido(anyString()))
                .thenReturn(List.of(pedidoDBResponse));

        when(pedidoDBMapper.dbToEntity(any(PedidoDB.class)))
                .thenReturn(pedidoResponse);

        List<Pedido> listaPedidoEncontrado = pedidoGatewayImpl.buscarPorStatusPedido(StatusPedido.RECEBIDO.name());

        Assertions.assertNotNull(listaPedidoEncontrado);
        Assertions.assertEquals(1, listaPedidoEncontrado.size());
    }

    @Test
    void buscarPorStatusPedido_Erro() {
        when(pedidoRepository.findByStatusPedido(anyString()))
                .thenThrow(new ArithmeticException());

        Exception exception = Assertions.assertThrows(ResourceFailedException.class, () ->
                pedidoGatewayImpl.buscarPorStatusPedido(StatusPedido.RECEBIDO.name()));

        String mensagemEsperada = "Erro na comunicação com o banco de dados";
        String mensagemAtual = exception.getMessage();

        Assertions.assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}