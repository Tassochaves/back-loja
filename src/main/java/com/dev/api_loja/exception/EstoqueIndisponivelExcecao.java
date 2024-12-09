package com.dev.api_loja.exception;

public class EstoqueIndisponivelExcecao extends RuntimeException {

    public EstoqueIndisponivelExcecao(String mensagem) {
        super(mensagem);
    }
}
