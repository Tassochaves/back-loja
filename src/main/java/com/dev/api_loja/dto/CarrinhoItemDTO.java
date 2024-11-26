package com.dev.api_loja.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CarrinhoItemDTO {

    private Long id;
    private int quantidade;
    private BigDecimal precoUnitario;
    private ProdutoDTO produto;
}
