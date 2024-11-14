package com.dev.api_loja.service.pedido;

import com.dev.api_loja.model.Pedido;

public interface IPedidoService {

    Pedido fazerPedido(Long userId);

    Pedido retornaPedido(Long pedidoId);
}
