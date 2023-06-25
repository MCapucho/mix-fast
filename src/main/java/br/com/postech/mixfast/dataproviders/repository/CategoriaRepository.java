package br.com.postech.mixfast.dataproviders.repository;

import br.com.postech.mixfast.dataproviders.model.db.CategoriaDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaDB, String> {

    Boolean existsByNome(String nome);
}
