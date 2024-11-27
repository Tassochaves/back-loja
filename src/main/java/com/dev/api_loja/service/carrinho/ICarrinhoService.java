package com.dev.api_loja.service.carrinho;

import java.math.BigDecimal;

import com.dev.api_loja.model.Carrinho;
import com.dev.api_loja.model.Usuario;

public interface ICarrinhoService {
    Carrinho retornaCarrinho(Long id);

    void limpaCarrinho(Long id);

    BigDecimal retornaPrecoTotal(Long id);

    Carrinho inicializaNovoCarrinho(Usuario usuario);

    Carrinho retornaCarrinhoPorUsuario(Long usuarioId);
}
