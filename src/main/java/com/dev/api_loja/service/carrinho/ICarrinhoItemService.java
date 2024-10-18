package com.dev.api_loja.service.carrinho;

import com.dev.api_loja.model.CarrinhoItem;

public interface ICarrinhoItemService {

    void adicionaItemCarrinho(Long carrinhoId, Long produtoId, int quantidade);
    void removeItemCarrinho(Long carrinhoId, Long produtoId);
    void atualizaQuantidadeItem(Long carrinhoId, Long produtoId, int quantidade);
    CarrinhoItem retornaItemCarrinho(Long carrinhoId, Long produtoId);
    
}
