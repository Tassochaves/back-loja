package com.dev.api_loja.controller;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.api_loja.dto.ProdutoDTO;
import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Produto;
import com.dev.api_loja.requisicao.AddProdutoRequest;
import com.dev.api_loja.requisicao.AtualizaProdutoRequest;
import com.dev.api_loja.resposta.ApiResponse;
import com.dev.api_loja.service.produto.IProdutoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/produtos")
public class ProdutoController {

    private final IProdutoService produtoService;

    @GetMapping("/todos")
    public ResponseEntity<ApiResponse> listarTodosProdutos(){
        List<Produto> produtos = produtoService.retornaTodosProdutos();

        List<ProdutoDTO> produtosConvertidos = produtoService.retornaProdutosConvertidos(produtos);

        return ResponseEntity.ok(new ApiResponse("Sucesso!", produtosConvertidos));
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<ApiResponse> listarProdutoPorId(@PathVariable Long produtoId){

        try {
            Produto produto = produtoService.retornaProdutoPorId(produtoId);

            ProdutoDTO produtoDTO = produtoService.convertParaDTO(produto);

            return ResponseEntity.ok(new ApiResponse("Sucesso!", produtoDTO));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/adicionar")
    public ResponseEntity<ApiResponse> adicionarProduto(@RequestBody AddProdutoRequest produtoRequest){

        try {
            Produto produto = produtoService.adicionaProduto(produtoRequest);
            return ResponseEntity.ok(new ApiResponse("Adicionado com sucesso!", produto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/atualizar/{produtoId}")
    public ResponseEntity<ApiResponse> atualizarProduto(@RequestBody AtualizaProdutoRequest atualizaRequest, @PathVariable Long produtoId){
        
        try {
            Produto produtoAtualizado = produtoService.atualizaProduto(atualizaRequest, produtoId);
            return ResponseEntity.ok(new ApiResponse("Produto atualizado com sucesso!", produtoAtualizado));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }       
    }

    @DeleteMapping("/excluir/{produtoId}")
    public ResponseEntity<ApiResponse> excluirProduto(@PathVariable Long produtoId){
        
        try {

            produtoService.deletaProdutoPorId(produtoId);
            return ResponseEntity.ok(new ApiResponse("Produto deletado com sucess!", produtoId));

        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/produto/nome-marca")
    public ResponseEntity<ApiResponse> listarProdutoPorMarcaENome(@RequestParam String nomeMarca, @RequestParam String nomeProduto){

        try {

            List<Produto> produtos = produtoService.retornaProdutosPorMarcaENome(nomeMarca, nomeProduto);
            List<ProdutoDTO> produtosConvertidos = produtoService.retornaProdutosConvertidos(produtos);

            if (produtos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Produtos não encontrado!", null));
            }

            return ResponseEntity.ok(new ApiResponse("Sucesso!", produtosConvertidos));
            
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }         
    }

    @GetMapping("/produto/categoria-marca")
    public ResponseEntity<ApiResponse> listarProdutoPorCategoriaEMarca(@RequestParam String categoria, @RequestParam String marca){

        try {

            List<Produto> produtos = produtoService.retornaProdutosPorCategoriaEMarca(categoria, marca);

            if (produtos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Produtos não encontrado!", null));
            }

            return ResponseEntity.ok(new ApiResponse("Sucesso!", produtos));
            
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }         
    }

    @GetMapping("/produto/nome")
    public ResponseEntity<ApiResponse> listarProdutoPorNome(@RequestParam String nome){

        try {

            List<Produto> produtos = produtoService.retornaProdutosPorNome(nome);

            if (produtos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Produtos não encontrado!", null));
            }

            return ResponseEntity.ok(new ApiResponse("Sucesso!", produtos));
            
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error: ", e.getMessage()));
        }         
    }

    @GetMapping("/produto/marca")
    public ResponseEntity<ApiResponse> listarProdutoPorMarca(@RequestParam String marca){

        try {

            List<Produto> produtos = produtoService.retornaProdutosPorMarca(marca);

            if (produtos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Produtos não encontrado!", null));
            }

            return ResponseEntity.ok(new ApiResponse("Sucesso!", produtos));
            
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }         
    }

    @GetMapping("/produto/categoria")
    public ResponseEntity<ApiResponse> listarProdutoPorCategoria(@RequestParam String categoria){

        try {

            List<Produto> produtos = produtoService.retornaProdutosPorCategoria(categoria);

            if (produtos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Produtos não encontrado!", null));
            }

            return ResponseEntity.ok(new ApiResponse("Sucesso!", produtos));
            
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }         
    }

    @GetMapping("/produto/quantidade")
    public ResponseEntity<ApiResponse> quantidadeProdutosPorMarcaENome(@RequestParam String marca, @RequestParam String nome){

        
        try {
            var quantidadeProduto = produtoService.contarProdutosPorMarcaENome(marca, nome);
            return ResponseEntity.ok(new ApiResponse("Quantidade de produto!", quantidadeProduto));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }
}
