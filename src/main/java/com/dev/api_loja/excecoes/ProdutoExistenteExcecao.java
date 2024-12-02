package com.dev.api_loja.excecoes;

public class ProdutoExistenteExcecao extends RuntimeException {

    public ProdutoExistenteExcecao(String mensagem) {
        super(mensagem);
    }
}
