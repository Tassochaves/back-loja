package com.dev.api_loja.service.produto;

import com.dev.api_loja.model.Produto;
import java.util.List;;

public interface IProdutoService {

    Produto adicionaProduto(Produto produto);

    Produto retornaProdutoPorId(Long id);

    void deletaProdutoPorId(Long id);

    void atualizaProduto(Produto produto, Long produtoId);

    List<Produto> retornaTodosProdutos();

    List<Produto> retornaProdutosPorCategoria(String categoria);

    List<Produto> retornaProdutosPorMarca(String marca);

    List<Produto> retornaProdutosPorCategoriaEMarca(String categoria, String marca);

    List<Produto> retornaProdutosPorNome(String nome);

    List<Produto> retornaProdutosPorMarcaENome(String categoria, String nome);

    List<Produto> contarProdutosPorMarcaENome(String marca, String nome);
}
