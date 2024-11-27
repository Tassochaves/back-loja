package com.dev.api_loja.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PedidoItemDTO {

    private Long produtoId;
    private String nome;
    private String marca;
    private int quantidade;
    private BigDecimal preco;
}
