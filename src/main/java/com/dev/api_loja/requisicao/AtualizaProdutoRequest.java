package com.dev.api_loja.requisicao;

import java.math.BigDecimal;

import com.dev.api_loja.model.Categoria;

import lombok.Data;

@Data
public class AtualizaProdutoRequest {

    private Long id;
    private String nome;
    private String marca; 
    private BigDecimal preco;
    private int estoque;
    private String descricao;
    private Categoria categoria;
}
