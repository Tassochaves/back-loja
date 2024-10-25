package com.dev.api_loja.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.resposta.ApiResponse;
import com.dev.api_loja.service.carrinho.ICarrinhoItemService;
import com.dev.api_loja.service.carrinho.ICarrinhoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carrinho-itens")
public class CarrinhoItemController {

    private final ICarrinhoItemService carrinhoItemService;
    private final ICarrinhoService carrinhoService;

    @PostMapping("/adicionar")
    public ResponseEntity<ApiResponse> adicionarItemParaCarrinho(
                @RequestParam(required = false) Long carrinhoId,
                @RequestParam Long produtoId,
                @RequestParam Integer quantidade){
        
        try {

            if (carrinhoId == null) {
                carrinhoId = carrinhoService.inicializaNovoCarrinho();
            }

            carrinhoItemService.adicionaItemCarrinho(carrinhoId, produtoId, quantidade);
            return ResponseEntity.ok(new ApiResponse("Item adicionado com sucesso!", null));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
            
    }

    @DeleteMapping("remover/{carrinhoId}/item/{itemId}")
    public ResponseEntity<ApiResponse> removerItemCarrinho(@PathVariable Long carrinhoId, @PathVariable Long itemId){
        
        try {
            carrinhoItemService.removeItemCarrinho(carrinhoId, itemId);
            return ResponseEntity.ok(new ApiResponse("Item removido com sucesso!", null));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("atualizar/{carrinhoId}/item/{itemId}")
    public ResponseEntity<ApiResponse> atualizarQuantidadeItem(
                @PathVariable Long carrinhoId, 
                @PathVariable Long itemId, 
                @RequestParam Integer quantidade){
        
        try {
            carrinhoItemService.atualizaQuantidadeItem(carrinhoId, itemId, quantidade);
            return ResponseEntity.ok(new ApiResponse("Item atualizado com sucesso!", null));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
