package com.dev.api_loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.api_loja.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
