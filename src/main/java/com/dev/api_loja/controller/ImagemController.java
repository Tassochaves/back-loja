package com.dev.api_loja.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.dev.api_loja.dto.ImagemDTO;
import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Imagem;
import com.dev.api_loja.resposta.ApiResponse;
import com.dev.api_loja.service.imagem.IImagemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/imagens")
public class ImagemController {
    
    private final IImagemService imagemService;

    @PostMapping("/envia")
    public ResponseEntity<ApiResponse> adicionaImagens(
        @RequestParam List<MultipartFile> arquivos,
        @RequestParam Long produtoId
    ){

        try {
            List<ImagemDTO> imagensDTOs = imagemService.adicionaImagens(arquivos, produtoId);

            return ResponseEntity.ok(new ApiResponse("Enviado com sucesso!", imagensDTOs));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Falha ao enviar imagens!", e.getMessage()));
        }
    }

    @GetMapping("/imagens/imagem/download/{imagemId}")
    public ResponseEntity<Resource> baixarImagens(@PathVariable Long imagemId) throws SQLException{

        Imagem imagem = imagemService.retornaImagemPorId(imagemId);
        ByteArrayResource conteudoBytes = new ByteArrayResource(imagem.getImagem().getBytes(1, (int) imagem.getImagem().length()));
        
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(imagem.getTipoArquivo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagem.getNomeArquivo() + "\"")
                .body(conteudoBytes);
    }

    @PutMapping("/imagem/{imagemId}/atualiza")
    public ResponseEntity<ApiResponse> atualizarImagem(@PathVariable Long imagemId, @RequestBody MultipartFile arquivo){
        
        try {
            Imagem imagem = imagemService.retornaImagemPorId(imagemId);
            
            if (imagem != null) {
                imagemService.atualizaImagem(arquivo, imagemId);
                return ResponseEntity.ok(new ApiResponse("Enviado com sucesso!", null));
            }
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Falha ao enviar imagens!", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/imagem/{imagemId}/exclui")
    public ResponseEntity<ApiResponse> excluiImagem(@PathVariable Long imagemId){
        
        try {
            Imagem imagem = imagemService.retornaImagemPorId(imagemId);
            
            if (imagem != null) {
                imagemService.deletaImagemPorId(imagemId);
                return ResponseEntity.ok(new ApiResponse("Excluido com sucesso!", null));
            }
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Falha ao excluir imagens!", INTERNAL_SERVER_ERROR));
    }
}
