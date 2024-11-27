package com.dev.api_loja.service.pedido;

import java.util.List;

import com.dev.api_loja.dto.PedidoDTO;
import com.dev.api_loja.model.Pedido;

public interface IPedidoService {

    Pedido fazerPedido(Long userId);

    PedidoDTO retornaPedido(Long pedidoId);

    List<PedidoDTO> retornaPedidoUsuario(Long usuarioId);

    PedidoDTO pedidoParaDTO(Pedido pedido);
}
