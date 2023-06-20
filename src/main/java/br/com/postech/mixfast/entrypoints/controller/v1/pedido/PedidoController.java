package br.com.postech.mixfast.entrypoints.controller.v1.pedido;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoBuscarTodosUseCase;
import br.com.postech.mixfast.core.usecase.interfaces.pedido.PedidoEnviarUseCase;
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

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping(value = "/v1/pedidos")
public class PedidoController {

    private final PedidoHttpMapper pedidoHttpMapper;
    private final PedidoEnviarUseCase pedidoEnviarUseCase;
    private final PedidoBuscarTodosUseCase pedidoBuscarTodosUseCase;

    @PostMapping
    public ResponseEntity<PedidoHttp> enviar(@Valid @RequestBody PedidoHttp pedidoHttp) {
        Pedido pedido = pedidoEnviarUseCase.enviar(pedidoHttpMapper.httpToEntity(pedidoHttp));
        log.info("Pedido enviado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoHttpMapper.entityToHttp(pedido));
    }

    @GetMapping
    public ResponseEntity<List<PedidoHttp>> buscarTodos() {
        List<Pedido> listaPedidos = pedidoBuscarTodosUseCase.buscarTodos();
        List<PedidoHttp> listaPedidosHttp = new ArrayList<>();

        listaPedidos.forEach(result -> {
            PedidoHttp pedidoHttp = pedidoHttpMapper.entityToHttp(result);
            listaPedidosHttp.add(pedidoHttp);
        });

        return ResponseEntity.status(HttpStatus.OK).body(listaPedidosHttp);
    }
}
