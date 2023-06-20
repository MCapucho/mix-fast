package br.com.postech.mixfast.dataproviders.repository;

import br.com.postech.mixfast.dataproviders.model.PedidoDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoDB, String> {

    @Query(value = "SELECT fila FROM tb_pedido ORDER BY data_pedido DESC limit 1", nativeQuery = true)
    Integer recuperarNumeroFila();

    Optional<PedidoDB> findByCodigo(String codigo);
}
