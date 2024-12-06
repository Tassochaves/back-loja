package com.dev.api_loja.controller;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.api_loja.exception.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Carrinho;
import com.dev.api_loja.resposta.ApiResponse;
import com.dev.api_loja.service.carrinho.ICarrinhoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carrinho")
public class CarrinhoController {

    private final ICarrinhoService carrinhoService;

    @GetMapping("/meu-carrinho/{carrinhoId}")
    public ResponseEntity<ApiResponse> retornaCarrinho(@PathVariable Long carrinhoId) {
        try {
            Carrinho carrinho = carrinhoService.retornaCarrinho(carrinhoId);
            return ResponseEntity.ok(new ApiResponse("Sucesso!", carrinho));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("limpar-carrinho/{carrinhoId}")
    public ResponseEntity<ApiResponse> limpaCarrinho(@PathVariable Long carrinhoId) {

        try {
            carrinhoService.limpaCarrinho(carrinhoId);
            return ResponseEntity.ok(new ApiResponse("Carrinho limpado com sucesso!", null));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/preco-total/{carrinhoId}")
    public ResponseEntity<ApiResponse> obtemPrecoTotal(@PathVariable Long carrinhoId) {

        try {
            BigDecimal precoTotal = carrinhoService.retornaPrecoTotal(carrinhoId);

            return ResponseEntity.ok(new ApiResponse("Preco total", precoTotal));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
