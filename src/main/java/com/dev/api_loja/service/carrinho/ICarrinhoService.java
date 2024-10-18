package com.dev.api_loja.service.carrinho;

import java.math.BigDecimal;

import com.dev.api_loja.model.Carrinho;

public interface ICarrinhoService {
    Carrinho retornaCarrinho(Long id);
    void limpaCarrinho(Long id);
    BigDecimal retornaPrecoTotal(Long id);
}
