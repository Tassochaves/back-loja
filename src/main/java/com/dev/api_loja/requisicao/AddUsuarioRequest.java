package com.dev.api_loja.requisicao;

import lombok.Data;

@Data
public class AddUsuarioRequest {

    private String primeiroNome;
    private String sobrenome;
    private String email;
    private String senha;
}
