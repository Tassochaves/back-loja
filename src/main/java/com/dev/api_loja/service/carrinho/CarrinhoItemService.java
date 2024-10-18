package com.dev.api_loja.service.carrinho;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Carrinho;
import com.dev.api_loja.model.CarrinhoItem;
import com.dev.api_loja.model.Produto;
import com.dev.api_loja.repository.CarrinhoItemRepository;
import com.dev.api_loja.repository.CarrinhoRepository;
import com.dev.api_loja.service.produto.IProdutoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CarrinhoItemService implements ICarrinhoItemService{

    private final CarrinhoItemRepository carrinhoItemRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final IProdutoService produtoService;
    private final ICarrinhoService carrinhoService;

    @Override
    public void adicionaItemCarrinho(Long carrinhoId, Long produtoId, int quantidade) {
        /*
        1 - Pegar o carrinho
        2 - Obter o produto
        3 - Verificar se o produto já está no carrinho
        4 - Se sim, aumente a quantidade com a quantidade solicitada
        5 - Se não, inicie uma nova entrada de item no carrinho. */
        Carrinho carrinho = carrinhoService.retornaCarrinho(carrinhoId);
        Produto produto = produtoService.retornaProdutoPorId(produtoId);

        CarrinhoItem carrinhoItem = carrinho.getItensCarrinho()
                .stream()
                .filter(itemCarrinho -> itemCarrinho.getProduto().getId().equals(produtoId))
                .findFirst().orElse(new CarrinhoItem());

        if (carrinhoItem.getId() == null) {
            carrinhoItem.setCarrinho(carrinho);
            carrinhoItem.setProduto(produto);
            carrinhoItem.setQuantidade(quantidade);
            carrinhoItem.setPrecoUnitario(produto.getPreco());
        }else{
            carrinhoItem.setQuantidade(carrinhoItem.getQuantidade() + quantidade);
        }

        carrinhoItem.calcularSubtotal();
        carrinho.adicionarItem(carrinhoItem);
        carrinhoItemRepository.save(carrinhoItem);
        carrinhoRepository.save(carrinho);
    }

    @Override
    public void removeItemCarrinho(Long carrinhoId, Long produtoId) {
        Carrinho carrinho = carrinhoService.retornaCarrinho(produtoId);
        CarrinhoItem itemParaRemover = retornaItemCarrinho(carrinhoId, produtoId);
        
        carrinho.removeItem(itemParaRemover);
        carrinhoRepository.save(carrinho);

    }

    @Override
    public void atualizaQuantidadeItem(Long carrinhoId, Long produtoId, int quantidade) {
        Carrinho carrinho = carrinhoService.retornaCarrinho(carrinhoId);

        carrinho.getItensCarrinho().stream()
                .filter(itemCarrinho -> itemCarrinho.getProduto().getId().equals(produtoId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantidade(quantidade);
                    item.setPrecoUnitario(item.getProduto().getPreco());
                    item.calcularSubtotal();
                });
        
        BigDecimal valorTotal = carrinho.getValorTotal();
        carrinho.setValorTotal(valorTotal);

        carrinhoRepository.save(carrinho);
    }

    @Override
    public CarrinhoItem retornaItemCarrinho(Long carrinhoId, Long produtoId){
        Carrinho carrinho = carrinhoService.retornaCarrinho(carrinhoId);

        return carrinho.getItensCarrinho()
                .stream()
                .filter(itemCarrinho -> itemCarrinho.getProduto().getId().equals(produtoId))
                .findFirst().orElseThrow(() -> new RecursoNaoEncontradoExcecao("Item nao encontrado!"));
    }

}
