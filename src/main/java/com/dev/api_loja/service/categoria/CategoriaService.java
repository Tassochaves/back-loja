package com.dev.api_loja.service.categoria;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.api_loja.model.Categoria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService implements ICategoriaService{@Override
    public Categoria retornaCategoriaPorId(Long id) {
        return null;
    }

    @Override
    public Categoria retornaCategoriaPorNome(String nome) {
        return null;
    }

    @Override
    public List<Categoria> retornaTodasCategorias() {
        return null;
    }

    @Override
    public Categoria adicionaCategoria(Categoria categoria) {
        return null;
    }

    @Override
    public Categoria atualizaCategoria(Categoria categoria) {
        return null;
    }

    @Override
    public void deletaCategoriaPorId(Long id) {
        
    }

}
