package com.dev.api_loja.service.imagem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dev.api_loja.dto.ImagemDTO;
import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Imagem;
import com.dev.api_loja.model.Produto;
import com.dev.api_loja.repository.ImagemRepository;
import com.dev.api_loja.service.produto.ProdutoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImagemService implements IImagemService{

    private final ImagemRepository imagemRepository;
    private final ProdutoService produtoService;


    @Override
    public Imagem retornaImagemPorId(Long id) {
        return imagemRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Nao existe imagem com o ID: " + id));
    }

    @Override
    public void deletaImagemPorId(Long id) {
        imagemRepository.findById(id).ifPresentOrElse(imagemRepository::delete, () -> {
            throw new RecursoNaoEncontradoExcecao("Nao existe imagem com o ID: " + id);
        });
    }

    @Override
    public List<ImagemDTO> adicionaImagens(List<MultipartFile> arquivos, Long produtoId) {
        
        Produto produto = produtoService.retornaProdutoPorId(produtoId);
        List<ImagemDTO> salvaImagemDTO = new ArrayList<>();

        for (MultipartFile arquivo : arquivos) {

            try {
                Imagem imagem = new Imagem();
                imagem.setNomeArquivo(arquivo.getOriginalFilename());
                imagem.setTipoArquivo(arquivo.getContentType());
                imagem.setImagem(new SerialBlob(arquivo.getBytes()));
                imagem.setProduto(produto);
                
                String recusoDownload = "/api/v1/imagens/imagem/download/";
                String downloadUrl = recusoDownload + imagem.getId();
                imagem.setDownloadUrl(downloadUrl);

                Imagem imagemSalva = imagemRepository.save(imagem);

                imagemSalva.setDownloadUrl(downloadUrl+imagemSalva.getId());
                imagemRepository.save(imagemSalva);

                //Obter as propriedades para o DTO.
                ImagemDTO imagemDTO = new ImagemDTO();
                imagemDTO.setImagemId(imagemSalva.getId());
                imagemDTO.setImagemNome(imagemSalva.getNomeArquivo());
                imagemDTO.setUrlDownload(imagemSalva.getDownloadUrl());

                salvaImagemDTO.add(imagemDTO);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return salvaImagemDTO;
    }

    @Override
    public void atualizaImagem(MultipartFile arquivo, Long imagemId) {
        Imagem imagem = retornaImagemPorId(imagemId);

        try {

            imagem.setNomeArquivo(arquivo.getOriginalFilename());
            imagem.setTipoArquivo(arquivo.getContentType());
            imagem.setImagem(new SerialBlob(arquivo.getBytes()));

            imagemRepository.save(imagem);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
