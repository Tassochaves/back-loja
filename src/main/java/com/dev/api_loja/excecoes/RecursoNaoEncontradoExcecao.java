package com.dev.api_loja.excecoes;

public class RecursoNaoEncontradoExcecao extends RuntimeException{

    public RecursoNaoEncontradoExcecao(String mensagem){
        super(mensagem);
    }
}
