package br.com.postech.mixfast.dataproviders.repository;

import br.com.postech.mixfast.dataproviders.model.db.PedidoDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoDB, String> {

    @Query(value = "SELECT MAX(fila) FROM tb_pedidos", nativeQuery = true)
    Integer recuperarNumeroFila();

    Optional<PedidoDB> findByCodigo(String codigo);

    @Modifying
    @Query("update PedidoDB p set p.statusPedido = ?1 where p.codigo = ?2")
    void atualizarStatusPedido(String status, String codigo);

    @Modifying
    @Query("update PedidoDB p set p.statusPagamento = ?1 where p.codigo = ?2")
    void atualizarStatusPagamento(String status, String codigo);

    List<PedidoDB> findByStatusPedido(String statusPedido);
}
