package com.dev.api_loja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.api_loja.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaNome(String categoria);

    List<Produto> findByMarca(String marca);

    List<Produto> findByCategoriaNomeAndMarca(String categoria, String marca);

    List<Produto> findByNome(String nome);

    List<Produto> findByMarcaAndNome(String marca, String nome);

    Long countByMarcaAndNome(String marca, String nome);

}
