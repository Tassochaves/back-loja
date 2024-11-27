package com.dev.api_loja.dto;

import java.util.List;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Long usuarioId;
    private String primeiroNome;
    private String sobrenome;
    private String email;
    private List<PedidoDTO> pedidos;
    private CarrinhoDTO carrinho;
}
