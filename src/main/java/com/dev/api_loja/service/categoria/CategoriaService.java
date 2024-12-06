package com.dev.api_loja.service.categoria;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.api_loja.exception.CategoriaExistenteExcecao;
import com.dev.api_loja.exception.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Categoria;
import com.dev.api_loja.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public Categoria retornaCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Categoria não encontrada!"));
    }

    @Override
    public Categoria retornaCategoriaPorNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }

    @Override
    public List<Categoria> retornaTodasCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria adicionaCategoria(Categoria categoria) {
        return Optional.of(categoria).filter(c -> !categoriaRepository.existsByNome(c.getNome()))
                .map(categoriaRepository::save)
                .orElseThrow(() -> new CategoriaExistenteExcecao(categoria.getNome() + " já existe"));
    }

    @Override
    public Categoria atualizaCategoria(Categoria categoria, Long id) {
        return Optional.ofNullable(retornaCategoriaPorId(id)).map(categoriaRetornada -> {
            categoriaRetornada.setNome(categoria.getNome());

            return categoriaRepository.save(categoriaRetornada);

        }).orElseThrow(() -> new RecursoNaoEncontradoExcecao("Categoria não encontrada!"));
    }

    @Override
    public void deletaCategoriaPorId(Long id) {
        categoriaRepository.findById(id).ifPresentOrElse(categoriaRepository::delete, () -> {
            throw new RecursoNaoEncontradoExcecao("Categoria não encontrada!");
        });
    }

}
