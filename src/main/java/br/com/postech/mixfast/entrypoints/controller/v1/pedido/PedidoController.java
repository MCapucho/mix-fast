package br.com.postech.mixfast.entrypoints.controller.v1.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.*;
import br.com.postech.mixfast.entrypoints.docs.PedidoDocumentable;
import br.com.postech.mixfast.entrypoints.http.PedidoHttp;
import br.com.postech.mixfast.entrypoints.http.mapper.PedidoHttpMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/v1/pedidos")
public class PedidoController implements PedidoDocumentable {

    private final PedidoHttpMapper pedidoHttpMapper;
    private final PedidoEmitirUseCase pedidoEmitirUseCase;
    private final PedidoBuscarTodosUseCase pedidoBuscarTodosUseCase;
    private final PedidoBuscarPorCodigoUseCase pedidoBuscarPorCodigoUseCase;
    private final PedidoAtualizarStatusUseCase pedidoAtualizarStatusUseCase;
    private final PedidoBuscarPorStatusUseCase pedidoBuscarPorStatusUseCase;

    @PostMapping
    public ResponseEntity<PedidoHttp> emitir(@Valid @RequestBody PedidoHttp pedidoHttp) {
        Pedido pedido = pedidoEmitirUseCase.emitir(pedidoHttpMapper.httpToEntity(pedidoHttp));
        PedidoHttp pedidoHttpEmitido = pedidoHttpMapper.entityToHttp(pedido);

        pedidoHttpEmitido.add(linkTo(methodOn(PedidoController.class)
                .preparar(pedidoHttpEmitido.getCodigo())).withSelfRel());
        pedidoHttpEmitido.add(linkTo(methodOn(PedidoController.class)
                .entregar(pedidoHttpEmitido.getCodigo())).withSelfRel());
        pedidoHttpEmitido.add(linkTo(methodOn(PedidoController.class)
                .finalizar(pedidoHttpEmitido.getCodigo())).withSelfRel());
        pedidoHttpEmitido.add(linkTo(methodOn(PedidoController.class)
                .cancelar(pedidoHttpEmitido.getCodigo())).withSelfRel());

        log.info("Pedido emitido com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoHttpEmitido);
    }

    @GetMapping
    public ResponseEntity<List<PedidoHttp>> buscarTodos() {
        List<Pedido> listaPedidos = pedidoBuscarTodosUseCase.buscarTodos();
        List<PedidoHttp> listaPedidosHttp = new ArrayList<>();

        listaPedidos.forEach(result -> {
            PedidoHttp pedidoHttp = pedidoHttpMapper.entityToHttp(result);
            listaPedidosHttp.add(pedidoHttp);
        });

        log.info("Lista de pedidos preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(listaPedidosHttp);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PedidoHttp> buscarPorCodigo(@PathVariable("codigo") String codigo) {
        Pedido pedido = pedidoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);
        log.info("Pedido encontrado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(pedidoHttpMapper.entityToHttp(pedido));
    }

    @GetMapping("/{codigo}/pagamento")
    public ResponseEntity<PedidoHttp> buscarPorCodigoStatusPagamento(@PathVariable("codigo") String codigo) {
        Pedido pedido = pedidoBuscarPorCodigoUseCase.buscarPorCodigo(codigo);

        PedidoHttp pedidoHttp = PedidoHttp.builder()
                .codigo(pedido.getCodigo())
                .build();

        log.info("Pedido encontrado com sucesso para exibição de status de pagamento");
        return ResponseEntity.status(HttpStatus.OK).body(pedidoHttp);
    }

    @GetMapping("/status")
    public ResponseEntity<List<PedidoHttp>> buscarPorStatus(@RequestParam("status") String status) {
        List<Pedido> listaPedidos = pedidoBuscarPorStatusUseCase.buscarPorStatusPedido(status);
        List<PedidoHttp> listaPedidosHttp = new ArrayList<>();

        listaPedidos.forEach(result -> {
            PedidoHttp pedidoHttp = pedidoHttpMapper.entityToHttp(result);
            listaPedidosHttp.add(pedidoHttp);
        });

        log.info("Lista de pedidos preenchida com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(listaPedidosHttp);
    }

    @PutMapping("/{codigo}/preparamento")
    public ResponseEntity<Void> preparar(@PathVariable("codigo") String codigo) {
        pedidoAtualizarStatusUseCase.preparar(codigo);
        log.info("Pedido atualizado em preparamento com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{codigo}/entrega")
    public ResponseEntity<Void> entregar(@PathVariable("codigo") String codigo) {
        pedidoAtualizarStatusUseCase.entregar(codigo);
        log.info("Pedido atualizado em entregue com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{codigo}/finalizado")
    public ResponseEntity<Void> finalizar(@PathVariable("codigo") String codigo) {
        pedidoAtualizarStatusUseCase.finalizar(codigo);
        log.info("Pedido atualizado em finalizado com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{codigo}/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable("codigo") String codigo) {
        pedidoAtualizarStatusUseCase.cancelar(codigo);
        log.info("Pedido atualizado em cancelado com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
