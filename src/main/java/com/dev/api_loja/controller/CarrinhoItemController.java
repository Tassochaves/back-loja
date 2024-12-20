package com.dev.api_loja.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.api_loja.exception.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Carrinho;
import com.dev.api_loja.model.Usuario;
import com.dev.api_loja.resposta.ApiResponse;
import com.dev.api_loja.service.carrinho.ICarrinhoItemService;
import com.dev.api_loja.service.carrinho.ICarrinhoService;
import com.dev.api_loja.service.usuario.IUsuarioService;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carrinho-itens")
public class CarrinhoItemController {

    private final ICarrinhoItemService carrinhoItemService;
    private final ICarrinhoService carrinhoService;
    private final IUsuarioService usuarioService;

    @PostMapping("/adicionar")
    public ResponseEntity<ApiResponse> adicionarItemParaCarrinho(
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade) {

        try {

            Usuario usuarioAutenticado = usuarioService.obtemUsuarioAutenticado();
            Carrinho carrinho = carrinhoService.inicializaNovoCarrinho(usuarioAutenticado);

            carrinhoItemService.adicionaItemCarrinho(carrinho.getId(), produtoId, quantidade);
            return ResponseEntity.ok(new ApiResponse("Item adicionado com sucesso!", null));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (JwtException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/carrinho/{carrinhoId}/item/{itemId}/remover")
    public ResponseEntity<ApiResponse> removerItemCarrinho(@PathVariable Long carrinhoId, @PathVariable Long itemId) {

        try {
            carrinhoItemService.removeItemCarrinho(carrinhoId, itemId);
            return ResponseEntity.ok(new ApiResponse("Item removido com sucesso!", null));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/carrinho/{carrinhoId}/produto/{produtoId}/atualizar")
    public ResponseEntity<ApiResponse> atualizarQuantidadeItem(
            @PathVariable Long carrinhoId,
            @PathVariable Long produtoId,
            @RequestParam Integer quantidade) {

        try {
            carrinhoItemService.atualizaQuantidadeItem(carrinhoId, produtoId, quantidade);
            return ResponseEntity.ok(new ApiResponse("Item atualizado com sucesso!", null));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
