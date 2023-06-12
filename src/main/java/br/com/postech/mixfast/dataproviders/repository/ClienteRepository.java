package br.com.postech.mixfast.dataproviders.repository;

import br.com.postech.mixfast.dataproviders.model.ClienteDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteDB, String> {

    ClienteDB findByCpf(String cpf);
    Boolean existsByCpf(String cpf);
    Boolean existsByEmail(String email);
}
