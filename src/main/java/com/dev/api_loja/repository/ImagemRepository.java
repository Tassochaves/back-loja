package com.dev.api_loja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.api_loja.model.Imagem;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {

    List<Imagem> findByProdutoId(Long produtoId);

}
