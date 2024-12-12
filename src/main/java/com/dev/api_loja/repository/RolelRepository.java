package com.dev.api_loja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.api_loja.model.Role;

public interface RolelRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNome(String role);

}
