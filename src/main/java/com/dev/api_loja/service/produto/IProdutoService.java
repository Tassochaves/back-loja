package com.dev.api_loja.service.produto;

import java.util.List;

import com.dev.api_loja.model.Produto;
import com.dev.api_loja.requisicao.AddProdutoRequest;
import com.dev.api_loja.requisicao.AtualizaProdutoRequest;

public interface IProdutoService {

    Produto adicionaProduto(AddProdutoRequest produtoRequest);

    Produto retornaProdutoPorId(Long id);

    void deletaProdutoPorId(Long id);

    Produto atualizaProduto(AtualizaProdutoRequest atualizaProdutoRequest, Long produtoId);

    List<Produto> retornaTodosProdutos();

    List<Produto> retornaProdutosPorCategoria(String categoria);

    List<Produto> retornaProdutosPorMarca(String marca);

    List<Produto> retornaProdutosPorCategoriaEMarca(String categoria, String marca);

    List<Produto> retornaProdutosPorNome(String nome);

    List<Produto> retornaProdutosPorMarcaENome(String categoria, String nome);

    Long contarProdutosPorMarcaENome(String marca, String nome);


}
