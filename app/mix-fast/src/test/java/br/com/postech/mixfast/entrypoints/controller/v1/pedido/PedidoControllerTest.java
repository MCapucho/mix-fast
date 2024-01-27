//package br.com.postech.mixfast.entrypoints.controller.v1.pedido;
//
//import br.com.postech.mixfast.core.entity.*;
//import br.com.postech.mixfast.core.entity.enums.StatusPagamento;
//import br.com.postech.mixfast.core.usecase.interfaces.pedido.*;
//import br.com.postech.mixfast.entrypoints.handler.RestExceptionHandler;
//import br.com.postech.mixfast.entrypoints.http.ClienteHttp;
//import br.com.postech.mixfast.entrypoints.http.FormaPagamentoHttp;
//import br.com.postech.mixfast.entrypoints.http.PedidoHttp;
//import br.com.postech.mixfast.entrypoints.http.PedidoProdutoHttp;
//import br.com.postech.mixfast.entrypoints.http.mapper.PedidoHttpMapper;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.json.JacksonTester;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
//@ExtendWith(MockitoExtension.class)
//class PedidoControllerTest {
//
//    public static final String CODIGO = UUID.randomUUID().toString();
//
//    @InjectMocks
//    private PedidoController pedidoController;
//    @Mock
//    private PedidoHttpMapper pedidoHttpMapper;
//    @Mock
//    private PedidoEmitirUseCase pedidoEmitirUseCase;
//    @Mock
//    private PedidoBuscarTodosUseCase pedidoBuscarTodosUseCase;
//    @Mock
//    private PedidoBuscarPorCodigoUseCase pedidoBuscarPorCodigoUseCase;
//    @Mock
//    private PedidoAtualizarStatusUseCase pedidoAtualizarStatusUseCase;
//    @Mock
//    private PedidoBuscarPorStatusUseCase pedidoBuscarPorStatusUseCase;
//
//    private JacksonTester<PedidoHttp> jacksonTester;
//    private MockMvc mvc;
//    private Pedido pedidoRequest;
//    private Pedido pedidoResponse;
//    private PedidoHttp pedidoHttpRequest;
//    private PedidoHttp pedidoHttpResponse;
//
//    @BeforeEach
//    void setUp() {
//        JacksonTester.initFields(this, new ObjectMapper());
//
//        mvc = MockMvcBuilders.standaloneSetup(pedidoController)
//                .setControllerAdvice(new RestExceptionHandler())
//                .build();
//
//        pedidoRequest = Pedido.builder()
//                .cliente(Cliente.builder()
//                        .codigo(UUID.randomUUID().toString())
//                        .build())
//                .formaPagamento(FormaPagamento.builder()
//                        .codigo(UUID.randomUUID().toString())
//                        .build())
//                .itens(List.of(PedidoProduto.builder()
//                                .produto(Produto.builder()
//                                        .codigo(UUID.randomUUID().toString())
//                                        .build())
//                                .quantidade(1)
//                        .build()))
//                .build();
//
//        pedidoResponse = Pedido.builder()
//                .codigo(UUID.randomUUID().toString())
//                .cliente(Cliente.builder()
//                        .codigo(UUID.randomUUID().toString())
//                        .build())
//                .formaPagamento(FormaPagamento.builder()
//                        .codigo(UUID.randomUUID().toString())
//                        .build())
//                .itens(List.of(PedidoProduto.builder()
//                        .produto(Produto.builder()
//                                .codigo(UUID.randomUUID().toString())
//                                .build())
//                        .quantidade(1)
//                        .build()))
//                .fila(1)
//                .valorTotal(new BigDecimal("200.00"))
//                .qrCode("abc123")
//                .statusPagamento(StatusPagamento.APROVADO)
//                .build();
//
//        pedidoHttpRequest = PedidoHttp.builder()
//                .cliente(ClienteHttp.builder()
//                        .codigo(UUID.randomUUID().toString())
//                        .build())
//                .formaPagamento(FormaPagamentoHttp.builder()
//                        .codigo(UUID.randomUUID().toString())
//                        .build())
//                .itens(List.of(PedidoProdutoHttp.builder()
//                        .produto(UUID.randomUUID().toString())
//                        .quantidade(1)
//                        .build()))
//                .build();
//
//        pedidoHttpResponse = PedidoHttp.builder()
//                .codigo(UUID.randomUUID().toString())
//                .cliente(ClienteHttp.builder()
//                        .codigo(UUID.randomUUID().toString())
//                        .build())
//                .formaPagamento(FormaPagamentoHttp.builder()
//                        .codigo(UUID.randomUUID().toString())
//                        .build())
//                .itens(List.of(PedidoProdutoHttp.builder()
//                        .produto(UUID.randomUUID().toString())
//                        .quantidade(1)
//                        .build()))
//                .fila(1)
//                .valorTotal(new BigDecimal("200.00"))
//                .qrCode("abc123")
//                .statusPagamento(StatusPagamento.APROVADO.name())
//                .build();
//    }
//
//    @SneakyThrows
//    @Test
//    void emitir() {
//        when(pedidoHttpMapper.httpToEntity(any(PedidoHttp.class)))
//                .thenReturn(pedidoRequest);
//
//        when(pedidoHttpMapper.entityToHttp(any(Pedido.class)))
//                .thenReturn(pedidoHttpResponse);
//
//        when(pedidoEmitirUseCase.emitir(any(Pedido.class)))
//                .thenReturn(pedidoResponse);
//
//        MockHttpServletResponse response =
//                mvc.perform(
//                        post("/v1/pedidos")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(jacksonTester.write(pedidoHttpRequest).getJson())
//                ).andReturn().getResponse();
//
//        assertEquals(response.getStatus(), HttpStatus.CREATED.value());
//    }
//
//    @SneakyThrows
//    @Test
//    void buscarTodos() {
//        when(pedidoBuscarTodosUseCase.buscarTodos())
//                .thenReturn(List.of(pedidoResponse));
//
//        when(pedidoHttpMapper.entityToHttp(any(Pedido.class)))
//                .thenReturn(pedidoHttpResponse);
//
//        MockHttpServletResponse response =
//                mvc.perform(
//                        get("/v1/pedidos")
//                                .contentType(MediaType.APPLICATION_JSON)
//                ).andReturn().getResponse();
//
//        assertEquals(response.getStatus(), HttpStatus.OK.value());
//    }
//
//    @SneakyThrows
//    @Test
//    void buscarPorCodigo() {
//        when(pedidoHttpMapper.entityToHttp(any(Pedido.class)))
//                .thenReturn(pedidoHttpResponse);
//
//        when(pedidoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
//                .thenReturn(pedidoResponse);
//
//        MockHttpServletResponse response =
//                mvc.perform(
//                        get("/v1/pedidos/{codigo}", CODIGO)
//                                .contentType(MediaType.APPLICATION_JSON)
//                ).andReturn().getResponse();
//
//        assertEquals(response.getStatus(), HttpStatus.OK.value());
//    }
//
//    @SneakyThrows
//    @Test
//    void buscarPorCodigoStatusPagamento() {
//        when(pedidoBuscarPorCodigoUseCase.buscarPorCodigo(anyString()))
//                .thenReturn(pedidoResponse);
//
//        MockHttpServletResponse response =
//                mvc.perform(
//                        get("/v1/pedidos/{codigo}/pagamento", CODIGO)
//                                .contentType(MediaType.APPLICATION_JSON)
//                ).andReturn().getResponse();
//
//        assertEquals(response.getStatus(), HttpStatus.OK.value());
//    }
//
//    @SneakyThrows
//    @Test
//    void buscarPorStatus() {
//        when(pedidoBuscarPorStatusUseCase.buscarPorStatusPedido(anyString()))
//                .thenReturn(List.of(pedidoResponse));
//
//        MockHttpServletResponse response =
//                mvc.perform(
//                        get("/v1/pedidos/status")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .param("status", "RECEBIDO")
//                ).andReturn().getResponse();
//
//        assertEquals(response.getStatus(), HttpStatus.OK.value());
//    }
//
//    @SneakyThrows
//    @Test
//    void preparar() {
//        doNothing().when(pedidoAtualizarStatusUseCase)
//                .preparar(anyString());
//
//        MockHttpServletResponse response =
//                mvc.perform(
//                        put("/v1/pedidos/{codigo}/preparamento", CODIGO)
//                ).andReturn().getResponse();
//
//        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
//    }
//
//    @SneakyThrows
//    @Test
//    void entregar() {
//        doNothing().when(pedidoAtualizarStatusUseCase)
//                .entregar(anyString());
//
//        MockHttpServletResponse response =
//                mvc.perform(
//                        put("/v1/pedidos/{codigo}/entrega", CODIGO)
//                ).andReturn().getResponse();
//
//        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
//    }
//
//    @SneakyThrows
//    @Test
//    void finalizar() {
//        doNothing().when(pedidoAtualizarStatusUseCase)
//                .finalizar(anyString());
//
//        MockHttpServletResponse response =
//                mvc.perform(
//                        put("/v1/pedidos/{codigo}/finalizado", CODIGO)
//                ).andReturn().getResponse();
//
//        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
//    }
//
//    @SneakyThrows
//    @Test
//    void cancelar() {
//        doNothing().when(pedidoAtualizarStatusUseCase)
//                .cancelar(anyString());
//
//        MockHttpServletResponse response =
//                mvc.perform(
//                        put("/v1/pedidos/{codigo}/cancelamento", CODIGO)
//                ).andReturn().getResponse();
//
//        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
//    }
//}