package com.dev.api_loja.service.categoria;

import java.util.List;

import com.dev.api_loja.model.Categoria;

public interface ICategoriaService {

    Categoria retornaCategoriaPorId(Long id);

    Categoria retornaCategoriaPorNome(String nome);

    List<Categoria> retornaTodasCategorias();

    Categoria adicionaCategoria(Categoria categoria);

    Categoria atualizaCategoria(Categoria categoria);

    void deletaCategoriaPorId(Long id);
}
