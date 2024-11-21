package com.dev.api_loja.service.pedido;

import java.util.List;

import com.dev.api_loja.model.Pedido;

public interface IPedidoService {

    Pedido fazerPedido(Long userId);

    Pedido retornaPedido(Long pedidoId);

    List<Pedido> retornaPedidoUsuario(Long usuarioId);
}
