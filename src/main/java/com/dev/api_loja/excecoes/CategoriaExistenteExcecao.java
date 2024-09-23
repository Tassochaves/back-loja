package com.dev.api_loja.excecoes;


public class CategoriaExistenteExcecao extends RuntimeException{

    public CategoriaExistenteExcecao(String mensagem) {
        super(mensagem);
    }

}
