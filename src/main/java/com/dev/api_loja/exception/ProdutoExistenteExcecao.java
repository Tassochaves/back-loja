package com.dev.api_loja.exception;

public class ProdutoExistenteExcecao extends RuntimeException {

    public ProdutoExistenteExcecao(String mensagem) {
        super(mensagem);
    }
}
