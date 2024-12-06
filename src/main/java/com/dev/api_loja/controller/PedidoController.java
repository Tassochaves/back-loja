package com.dev.api_loja.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.api_loja.dto.PedidoDTO;
import com.dev.api_loja.exception.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Pedido;
import com.dev.api_loja.resposta.ApiResponse;
import com.dev.api_loja.service.pedido.IPedidoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/pedidos")
public class PedidoController {

    private final IPedidoService pedidoService;

    @PostMapping("/pedido")
    public ResponseEntity<ApiResponse> criarPedido(@RequestParam Long usuarioId) {

        try {

            Pedido pedido = pedidoService.fazerPedido(usuarioId);
            PedidoDTO pedidoDTO = pedidoService.pedidoParaDTO(pedido);
            return ResponseEntity.ok(new ApiResponse("Pedido realizado com sucesso", pedidoDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Erro encontrado: ", e.getMessage()));
        }

    }

    @GetMapping("/{pedidoId}/pedido")
    public ResponseEntity<ApiResponse> retornaPedidoPorId(@PathVariable Long pedidoId) {

        try {
            PedidoDTO pedidoDTO = pedidoService.retornaPedido(pedidoId);

            return ResponseEntity.ok(new ApiResponse("Pedido encontrado com sucesso!", pedidoDTO));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Oops! ", e.getMessage()));
        }
    }

    @GetMapping("/{usuarioId}/usuario-pedidos")
    public ResponseEntity<ApiResponse> retornaUsuarioPedido(@PathVariable Long usuarioId) {

        try {
            List<PedidoDTO> pedidos = pedidoService.retornaPedidoUsuario(usuarioId);

            return ResponseEntity.ok(new ApiResponse("Pedido encontrado com sucesso!", pedidos));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Oops! ", e.getMessage()));
        }
    }
}
