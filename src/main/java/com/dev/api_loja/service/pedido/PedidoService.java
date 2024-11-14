package com.dev.api_loja.service.pedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.api_loja.enums.StatusPedido;
import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Carrinho;
import com.dev.api_loja.model.Pedido;
import com.dev.api_loja.model.PedidoItem;
import com.dev.api_loja.model.Produto;
import com.dev.api_loja.repository.PedidoRepository;
import com.dev.api_loja.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService implements IPedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    @Override
    public Pedido fazerPedido(Long userId) {
        return null;
    }

    private Pedido criarPedido(Carrinho carrinho) {

        Pedido pedido = new Pedido();

        // - Configuar usuario...
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        pedido.setDataPedido(LocalDate.now());
        return pedido;
    }

    @Override
    public Pedido retornaPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Pedido nao encontrado!"));
    }

    private List<PedidoItem> criarItensPedido(Pedido pedido, Carrinho carrinho) {
        return carrinho.getItensCarrinho().stream().map(carrinhoItem -> {
            Produto produto = carrinhoItem.getProduto();
            produto.setEstoque(produto.getEstoque() - carrinhoItem.getQuantidade());

            produtoRepository.save(produto);

            return new PedidoItem(pedido, produto, carrinhoItem.getQuantidade(), carrinhoItem.getPrecoUnitario());
        }).toList();
    }

    private BigDecimal calculaVatorTotalPedido(List<PedidoItem> listaItensPedido) {

        return listaItensPedido
                .stream()
                .map(item -> item.getPreco().multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
