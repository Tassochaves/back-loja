package com.dev.api_loja.excecoes;

public class UsuarioExistenteExcecao extends RuntimeException {

    public UsuarioExistenteExcecao(String mensagem) {
        super(mensagem);
    }
}
