package com.dev.api_loja.excecoes;

public class ProdutoNaoEncontradoExcecao extends RuntimeException{

    public ProdutoNaoEncontradoExcecao(String mensagem){
        super(mensagem);
    }
}
