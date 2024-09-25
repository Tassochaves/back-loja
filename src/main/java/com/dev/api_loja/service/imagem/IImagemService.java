package com.dev.api_loja.service.imagem;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dev.api_loja.dto.ImagemDTO;
import com.dev.api_loja.model.Imagem;

public interface IImagemService {

    Imagem retornaImagemPorId(Long id);

    void deletaImagemPorId(Long id);

    List<ImagemDTO> adicionaImagens(List<MultipartFile> arquivos, Long produtoId);

    void atualizaImagem(MultipartFile arquivo, Long imagemId);
}
