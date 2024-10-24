package com.dev.api_loja.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.resposta.ApiResponse;
import com.dev.api_loja.service.carrinho.ICarrinhoItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carrinho-itens")
public class CarrinhoItemController {

    private final ICarrinhoItemService carrinhoItemService;

    @PostMapping("/adicionar")
    public ResponseEntity<ApiResponse> adicionaItemParaCarrinho(
                @RequestParam Long carrinhoId,
                @RequestParam Long produtoId,
                @RequestParam Integer quantidade){
        
        try {
            carrinhoItemService.adicionaItemCarrinho(carrinhoId, produtoId, quantidade);
            return ResponseEntity.ok(new ApiResponse("Item adicionado com sucesso!", null));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
            
    }

}
