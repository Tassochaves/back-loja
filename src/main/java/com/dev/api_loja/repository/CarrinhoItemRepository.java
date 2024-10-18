package com.dev.api_loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.api_loja.model.CarrinhoItem;

public interface CarrinhoItemRepository extends JpaRepository<CarrinhoItem, Long>{

    void deleteAllByCarrinhoId(Long id);

}
