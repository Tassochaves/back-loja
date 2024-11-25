package com.dev.api_loja.service.pedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dev.api_loja.dto.PedidoDTO;
import com.dev.api_loja.enums.StatusPedido;
import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Carrinho;
import com.dev.api_loja.model.Pedido;
import com.dev.api_loja.model.PedidoItem;
import com.dev.api_loja.model.Produto;
import com.dev.api_loja.repository.PedidoRepository;
import com.dev.api_loja.repository.ProdutoRepository;
import com.dev.api_loja.service.carrinho.CarrinhoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService implements IPedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final CarrinhoService carrinhoService;
    private final ModelMapper modelMapper;

    @Override
    public Pedido fazerPedido(Long usuarioId) {

        Carrinho carrinho = carrinhoService.retornaCarrinhoPorUsuario(usuarioId);

        Pedido pedido = criarPedido(carrinho);
        List<PedidoItem> listaItensPedido = criarItensPedido(pedido, carrinho);

        pedido.setItensPedido(new HashSet<>(listaItensPedido));
        pedido.setTotalParaPagar(calculaVatorTotalPedido(listaItensPedido));

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        carrinhoService.limpaCarrinho(carrinho.getId());
        return pedidoSalvo;
    }

    private Pedido criarPedido(Carrinho carrinho) {

        Pedido pedido = new Pedido();

        pedido.setUsuario(carrinho.getUsuario());
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        pedido.setDataPedido(LocalDate.now());
        return pedido;
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

    @Override
    public PedidoDTO retornaPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).map(this::pedidoParaDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Pedido nao encontrado!"));
    }

    @Override
    public List<PedidoDTO> retornaPedidoUsuario(Long usuarioId) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);
        return pedidos.stream().map(this::pedidoParaDTO).toList();
    }

    private PedidoDTO pedidoParaDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }

}
