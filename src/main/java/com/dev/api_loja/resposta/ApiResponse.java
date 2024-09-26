package com.dev.api_loja.resposta;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse {

    private String mensagem;
    private Object dados;
}
