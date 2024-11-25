package com.dev.api_loja.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.dev.api_loja.enums.StatusPedido;
import com.dev.api_loja.model.PedidoItem;

import lombok.Data;

@Data
public class PedidoDTO {

    private Long pedidoId;
    private Long usuarioId;
    private LocalDateTime dataPedido;
    private BigDecimal totalParaPagar;
    private StatusPedido statusPedido;
    private List<PedidoItem> itens;
}
