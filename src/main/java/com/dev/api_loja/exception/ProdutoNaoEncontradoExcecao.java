package com.dev.api_loja.exception;

public class ProdutoNaoEncontradoExcecao extends RuntimeException {

    public ProdutoNaoEncontradoExcecao(String mensagem) {
        super(mensagem);
    }
}
