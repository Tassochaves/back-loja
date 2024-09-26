package com.dev.api_loja.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.api_loja.excecoes.CategoriaExistenteExcecao;
import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Categoria;
import com.dev.api_loja.resposta.ApiResponse;
import com.dev.api_loja.service.categoria.ICategoriaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categorias")
public class CategoriaController {

    private final ICategoriaService categoriaService;

    @GetMapping("/todas")
    public ResponseEntity<ApiResponse> listaTodasCategorias(){
        
        try {
            List<Categoria> categorias = categoriaService.retornaTodasCategorias();
            return ResponseEntity.ok(new ApiResponse("Encontrado", categorias));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Identificado error: ", INTERNAL_SERVER_ERROR));
        }
        
    }

    @PostMapping("/adicionar")
    public ResponseEntity<ApiResponse> adicionarCategoria(@RequestBody Categoria categoriaNome){
        
        try {
            Categoria categoria = categoriaService.adicionaCategoria(categoriaNome);
            return ResponseEntity.ok(new ApiResponse("Sucesso", categoria));
        } catch (CategoriaExistenteExcecao existe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(existe.getMessage(), null));
        }

    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<ApiResponse> listaCategoriaPorId(@PathVariable Long id){
        try {
            Categoria categoria = categoriaService.retornaCategoriaPorId(id);
            return ResponseEntity.ok(new ApiResponse("Encontrado", categoria));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/categoria/{nome}")
    public ResponseEntity<ApiResponse> listaCategoriaPorNome(@PathVariable String nome){
        try {
            Categoria categoria = categoriaService.retornaCategoriaPorNome(nome);
            return ResponseEntity.ok(new ApiResponse("Encontrado", categoria));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<ApiResponse> excluirCategoria(@PathVariable Long id){
        try {
            categoriaService.deletaCategoriaPorId(id);
            return ResponseEntity.ok(new ApiResponse("Encontrado", null));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/alterar/{id}")
    public ResponseEntity<ApiResponse> alterarCategoria(@PathVariable Long id, @RequestBody Categoria categoria){
        try {
            Categoria categoriaAtualizada = categoriaService.atualizaCategoria(categoria, id);
            return ResponseEntity.ok(new ApiResponse("Atualizada com sucesso! ", categoriaAtualizada));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
