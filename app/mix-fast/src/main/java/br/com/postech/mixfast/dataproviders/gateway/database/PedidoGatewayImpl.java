package br.com.postech.mixfast.dataproviders.gateway.database;

import br.com.postech.mixfast.core.entity.Pedido;
import br.com.postech.mixfast.core.gateway.PedidoGateway;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
import br.com.postech.mixfast.dataproviders.model.db.PedidoDB;
import br.com.postech.mixfast.dataproviders.model.db.ProdutoDB;
import br.com.postech.mixfast.dataproviders.model.mapper.PedidoDBMapper;
import br.com.postech.mixfast.dataproviders.repository.PedidoRepository;
import br.com.postech.mixfast.dataproviders.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class PedidoGatewayImpl implements PedidoGateway {

    private static final String BANCO_DE_DADOS = "Erro na comunicação com o banco de dados";
    private static final String FINALIZADO = "FINALIZADO";

    private final PedidoDBMapper pedidoDBMapper;
    private final PedidoRepository pedidoRepository;

    private final ProdutoRepository produtoRepository;

    @Transactional
    @Override
    public Pedido emitir(Pedido pedido) {
        try {
            PedidoDB pedidoDB = pedidoDBMapper.entityToDB(pedido);
            validarItens(pedidoDB);
            pedidoDB.calcularValorTotal();
            geradorNumeroFila(pedidoDB);
            return pedidoDBMapper.dbToEntity(pedidoRepository.save(pedidoDB));
        } catch (Exception e) {
            log.error("Erro ao emitir um pedido", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Transactional
    @Override
    public List<Pedido> buscarTodos() {
        try {
            List<PedidoDB> listaPedidosDB = pedidoRepository.findAll(Sort.by(Sort.Direction.ASC, "fila"));
            List<Pedido> listaPedidos = new ArrayList<>();

            listaPedidosDB.forEach(result -> {
                if (!result.getStatusPedido().equals(FINALIZADO)) {
                    Pedido pedido = pedidoDBMapper.dbToEntity(result);
                    listaPedidos.add(pedido);
                }
            });

            return listaPedidos;
        } catch (Exception e) {
            log.error("Erro ao buscar todos pedidos", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Transactional
    @Override
    public Pedido buscarPorCodigo(String codigo) {
        try {
            return pedidoRepository.findByCodigo(codigo)
                    .stream()
                    .map(pedidoDBMapper::dbToEntity)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            log.error("Erro ao buscar um pedido por código", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Transactional
    @Override
    public void atualizarStatusPedido(Pedido pedido) {
        try {
            pedidoRepository.atualizarStatusPedido(String.valueOf(pedido.getStatusPedido()), pedido.getCodigo());
        } catch (Exception e) {
            log.error("Erro ao atualizar o status de um pedido", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Transactional
    @Override
    public void atualizarStatusPagamento(Pedido pedido) {
        try {
            pedidoRepository.atualizarStatusPagamento(String.valueOf(pedido.getStatusPagamento()), pedido.getCodigo());
        } catch (Exception e) {
            log.error("Erro ao atualizar o status de pagamento de um pedido", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Transactional
    @Override
    public List<Pedido> buscarPorStatusPedido(String statusPedido) {
        try {
            List<PedidoDB> listaPedidosDB = pedidoRepository.findByStatusPedido(statusPedido);
            List<Pedido> listaPedidos = new ArrayList<>();

            listaPedidosDB.forEach(result -> {
                Pedido pedido = pedidoDBMapper.dbToEntity(result);
                listaPedidos.add(pedido);
            });

            return listaPedidos;
        } catch (Exception e) {
            log.error(String.format("Erro ao buscar os pedidos por status %s", statusPedido), e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    private void validarItens(PedidoDB pedidoDB) {
        pedidoDB.getItens().forEach(element -> {
            ProdutoDB produtoDB = produtoRepository.findById(element.getProduto().getCodigo())
                    .orElseThrow(() -> new ResourceFailedException("Produto informado não encontrado na base"));

            element.setPedido(pedidoDB);
            element.setProduto(produtoDB);
            element.setPrecoUnitario(produtoDB.getPreco());
        });
    }

    private void geradorNumeroFila(PedidoDB pedidoDB) {
        Integer numeroFila = pedidoRepository.recuperarNumeroFila();
        if (numeroFila == null) {
            numeroFila = 1;
        } else {
            numeroFila++;
        }
        pedidoDB.setFila(numeroFila);
    }
}
