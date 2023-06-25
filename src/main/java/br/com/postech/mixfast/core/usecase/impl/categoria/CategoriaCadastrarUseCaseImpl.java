package br.com.postech.mixfast.core.usecase.impl.categoria;

import br.com.postech.mixfast.core.entity.Categoria;
import br.com.postech.mixfast.core.exception.categoria.CategoriaBadRequestException;
import br.com.postech.mixfast.core.exception.categoria.CategoriaDuplicatedException;
import br.com.postech.mixfast.core.gateway.CategoriaGateway;
import br.com.postech.mixfast.core.usecase.interfaces.categoria.CategoriaCadastrarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoriaCadastrarUseCaseImpl implements CategoriaCadastrarUseCase {

    private final CategoriaGateway categoriaGateway;

    @Override
    public Categoria cadastrar(Categoria categoria) {
        if (categoriaGateway.encontrarPorNome(categoria.getNome())) {
            throw new CategoriaDuplicatedException("Categoria informada já cadastrada");
        }

        Categoria categoriaCadastrada = categoriaGateway.cadastrarOuAtualizar(categoria);

        if (categoriaCadastrada == null) {
            throw new CategoriaBadRequestException("Cadastro de categoria não foi concluído");
        }

        return categoriaCadastrada;
    }
}
