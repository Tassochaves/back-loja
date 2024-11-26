package com.dev.api_loja.dto;

import java.math.BigDecimal;
import java.util.List;

import com.dev.api_loja.model.Categoria;

import lombok.Data;

@Data
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String marca;
    private BigDecimal preco;
    private int estoque;
    private String descricao;
    private Categoria categoria;
    private List<ImagemDTO> imagens;
}
