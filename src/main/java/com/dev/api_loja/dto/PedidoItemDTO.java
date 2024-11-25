package com.dev.api_loja.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PedidoItemDTO {

    private Long produtoId;
    private String nomeProduto;
    private int quantidade;
    private BigDecimal preco;
}
