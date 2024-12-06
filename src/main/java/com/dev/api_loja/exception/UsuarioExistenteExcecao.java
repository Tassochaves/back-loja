package com.dev.api_loja.exception;

public class UsuarioExistenteExcecao extends RuntimeException {

    public UsuarioExistenteExcecao(String mensagem) {
        super(mensagem);
    }
}
