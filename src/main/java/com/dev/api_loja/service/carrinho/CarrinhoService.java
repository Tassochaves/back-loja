package com.dev.api_loja.service.carrinho;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Carrinho;
import com.dev.api_loja.repository.CarrinhoItemRepository;
import com.dev.api_loja.repository.CarrinhoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CarrinhoService implements ICarrinhoService{

    private final CarrinhoRepository carrinhoRepository;
    private final CarrinhoItemRepository carrinhoItemRepository;

    @Override
    public Carrinho retornaCarrinho(Long id) {
        Carrinho carrinho = carrinhoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Carrinho nao encontrado!"));

        BigDecimal valorTotal = carrinho.getValorTotal();
        carrinho.setValorTotal(valorTotal);
        return carrinhoRepository.save(carrinho);
    }

    @Override
    public void limpaCarrinho(Long id) {
        Carrinho carrinho = retornaCarrinho(id);
        carrinhoItemRepository.deleteAllByCarrinhoId(id);

        carrinho.getItensCarrinho().clear();
        carrinhoRepository.deleteById(id);
    }

    @Override
    public BigDecimal retornaPrecoTotal(Long id) {
        Carrinho carrinho = retornaCarrinho(id);

        return carrinho.getValorTotal();
    }

}
