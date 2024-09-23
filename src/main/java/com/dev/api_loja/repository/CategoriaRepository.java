package com.dev.api_loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.api_loja.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Categoria findByNome(String nome);

    boolean existsByNome(String nome);

}
