package br.com.postech.mixfast.dataproviders.repository;

import br.com.postech.mixfast.dataproviders.model.db.ProdutoDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoDB, String> {

    List<ProdutoDB> findByCategoriaCodigo(String categoria);
}
