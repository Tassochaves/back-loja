package com.dev.api_loja.service.carrinho;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Carrinho;
import com.dev.api_loja.repository.CarrinhoItemRepository;
import com.dev.api_loja.repository.CarrinhoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CarrinhoService implements ICarrinhoService{

    private final CarrinhoRepository carrinhoRepository;
    private final CarrinhoItemRepository carrinhoItemRepository;
    private final AtomicLong geradorIdCarrinho = new AtomicLong(0);

    @Override
    public Carrinho retornaCarrinho(Long id) {
        Carrinho carrinho = carrinhoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Carrinho nao encontrado!"));

        BigDecimal valorTotal = carrinho.getValorTotal();
        carrinho.setValorTotal(valorTotal);
        return carrinhoRepository.save(carrinho);
    }

    @Transactional
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

    @Override
    public Long inicializaNovoCarrinho(){
        Carrinho novoCarrinho = new Carrinho();
        Long novoIdCarrinho = geradorIdCarrinho.incrementAndGet();

        novoCarrinho.setId(novoIdCarrinho);
        return carrinhoRepository.save(novoCarrinho).getId();
    }

}
