package com.dev.api_loja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.api_loja.model.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {

    Optional<Papel> findByNome(String papel);

}
