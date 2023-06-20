package br.com.postech.mixfast.dataproviders.gateway.database;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.gateway.CategoriaGateway;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
import br.com.postech.mixfast.dataproviders.model.db.CategoriaDB;
import br.com.postech.mixfast.dataproviders.model.mapper.CategoriaDBMapper;
import br.com.postech.mixfast.dataproviders.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class CategoriaGatewayImpl implements CategoriaGateway {

    private static final String BANCO_DE_DADOS = "Erro na comunicação com o banco de dados";

    private final CategoriaDBMapper categoriaDBMapper;
    private final CategoriaRepository categoriaRepository;

    @Override
    public Categoria cadastrarOuAtualizar(Categoria categoria) {
        try {
            CategoriaDB categoriaDB = categoriaRepository.save(categoriaDBMapper.entityToDB(categoria));
            return categoriaDBMapper.dbToEntity(categoriaDB);
        } catch (Exception e) {
            log.error("Erro ao cadastrar/atualizar uma categoria", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Transactional
    @Override
    public List<Categoria> buscarTodas() {
        try {
            List<CategoriaDB> listaCategoriasDB = categoriaRepository.findAll();
            List<Categoria> listaCategorias = new ArrayList<>();

            listaCategoriasDB.forEach(result -> {
                Categoria categoria = categoriaDBMapper.dbToEntity(result);
                listaCategorias.add(categoria);
            });

            return listaCategorias;
        } catch (Exception e) {
            log.error("Erro ao buscar todas categorias", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Override
    public Categoria buscarPorCodigo(String codigo) {
        try {
            return categoriaRepository.findById(codigo)
                    .stream()
                    .map(categoriaDBMapper::dbToEntity)
                    .findAny()
                    .orElse(null);
        } catch (Exception e) {
            log.error("Erro ao buscar uma categoria por código", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Override
    public void deletarPorCodigo(String codigo) {
        try {
            categoriaRepository.deleteById(codigo);
        } catch (Exception e) {
            log.error("Erro ao deletar uma categoria por código", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }
}
