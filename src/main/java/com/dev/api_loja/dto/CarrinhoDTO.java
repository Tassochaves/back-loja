package com.dev.api_loja.dto;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;

@Data
public class CarrinhoDTO {
    private Long carrinhoId;
    private BigDecimal valorTotal;
    private Set<CarrinhoItemDTO> itens;
}
