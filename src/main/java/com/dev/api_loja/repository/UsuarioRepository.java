package com.dev.api_loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.api_loja.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Usuario findByEmail(String email);

}
