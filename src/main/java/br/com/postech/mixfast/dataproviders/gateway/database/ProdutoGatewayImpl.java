package br.com.postech.mixfast.dataproviders.gateway.database;

import br.com.postech.mixfast.core.entity.Produto;
import br.com.postech.mixfast.core.gateway.ProdutoGateway;
import br.com.postech.mixfast.dataproviders.exception.ResourceFailedException;
import br.com.postech.mixfast.dataproviders.model.db.ProdutoDB;
import br.com.postech.mixfast.dataproviders.model.mapper.ProdutoDBMapper;
import br.com.postech.mixfast.dataproviders.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class ProdutoGatewayImpl implements ProdutoGateway {

    private static final String BANCO_DE_DADOS = "Erro na comunicação com o banco de dados";

    private final ProdutoDBMapper produtoDBMapper;
    private final ProdutoRepository produtoRepository;

    @Override
    public Produto cadastrarOuAtualizar(Produto produto) {
        try {
            ProdutoDB produtoDB = produtoRepository.save(produtoDBMapper.entityToDB(produto));
            return produtoDBMapper.dbToEntity(produtoDB);
        } catch (Exception e) {
            log.error("Erro ao cadastrar ou atualizar um produto", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Transactional
    @Override
    public List<Produto> buscarTodos() {
        List<ProdutoDB> listaProdutosDB = produtoRepository.findAll();
        List<Produto> listaProduto = new ArrayList<>();

        listaProdutosDB.forEach(result -> {
            Produto produto = produtoDBMapper.dbToEntity(result);
            listaProduto.add(produto);
        });

        return listaProduto;
    }

    @Override
    public Produto buscarPorCodigo(String codigo) {
        return produtoRepository.findById(codigo)
                .map(produtoDBMapper::dbToEntity)
                .orElse(null);
    }

    @Override
    public void deletarPorCodigo(String codigo) {
        try {
            produtoRepository.deleteById(codigo);
        } catch (Exception e) {
            log.error("Erro ao remover um produto", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }

    @Transactional
    @Override
    public List<Produto> buscarPorCategoria(String categoria) {
        try {
            List<ProdutoDB> listaProdutosDB = produtoRepository.findByCategoriaCodigo(categoria);
            List<Produto> listaProdutos = new ArrayList<>();

            listaProdutosDB.forEach(result -> {
                Produto produto = produtoDBMapper.dbToEntity(result);
                listaProdutos.add(produto);
            });

            return listaProdutos;
        } catch (Exception e) {
            log.error("Erro ao remover um produto", e);
            throw new ResourceFailedException(BANCO_DE_DADOS);
        }
    }
}
