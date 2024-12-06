package com.dev.api_loja.service.produto;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dev.api_loja.dto.ImagemDTO;
import com.dev.api_loja.dto.ProdutoDTO;
import com.dev.api_loja.exception.ProdutoExistenteExcecao;
import com.dev.api_loja.exception.ProdutoNaoEncontradoExcecao;
import com.dev.api_loja.model.Categoria;
import com.dev.api_loja.model.Imagem;
import com.dev.api_loja.model.Produto;
import com.dev.api_loja.repository.CategoriaRepository;
import com.dev.api_loja.repository.ImagemRepository;
import com.dev.api_loja.repository.ProdutoRepository;
import com.dev.api_loja.requisicao.AddProdutoRequest;
import com.dev.api_loja.requisicao.AtualizaProdutoRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService implements IProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ImagemRepository imagemRepository;
    private final ModelMapper modelMapper;

    @Override
    public Produto adicionaProduto(AddProdutoRequest produtoRequest) {

        if (produtoExistente(produtoRequest.getNome(), produtoRequest.getMarca())) {
            throw new ProdutoExistenteExcecao(
                    produtoRequest.getNome() + " da Marca " + produtoRequest.getMarca() + " ja existe!");
        }

        Categoria categoria = Optional
                .ofNullable(categoriaRepository.findByNome(produtoRequest.getCategoria().getNome()))
                .orElseGet(() -> {
                    Categoria novaCategoria = new Categoria(produtoRequest.getCategoria().getNome());
                    return categoriaRepository.save(novaCategoria);
                });

        produtoRequest.setCategoria(categoria);
        return produtoRepository.save(criarProduto(produtoRequest, categoria));
    }

    // Metodos auxiliares
    private boolean produtoExistente(String nome, String marca) {
        return produtoRepository.existsByNomeAndMarca(nome, marca);
    }

    private Produto criarProduto(AddProdutoRequest produtoRequest, Categoria categoria) {
        return new Produto(
                produtoRequest.getDescricao(),
                produtoRequest.getEstoque(),
                produtoRequest.getMarca(),
                produtoRequest.getNome(),
                produtoRequest.getPreco(),
                categoria);
    }

    @Override
    public Produto retornaProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoExcecao("Produto não encontrado!"));
    }

    @Override
    public void deletaProdutoPorId(Long id) {
        produtoRepository.findById(id)
                .ifPresentOrElse(produtoRepository::delete, () -> {
                    throw new ProdutoNaoEncontradoExcecao("Produto não encontrado!");
                });
    }

    @Override
    public Produto atualizaProduto(AtualizaProdutoRequest atualizaProdutoRequest, Long produtoId) {
        return produtoRepository.findById(produtoId)
                .map(produtoExistente -> atualizarProdutoExistente(produtoExistente, atualizaProdutoRequest))
                .map(produtoRepository::save)
                .orElseThrow(() -> new ProdutoNaoEncontradoExcecao("Produto não encontrado!"));
    }

    // Metodo auxiliar
    public Produto atualizarProdutoExistente(Produto produtoExistente, AtualizaProdutoRequest atualizaProdutoRequest) {
        produtoExistente.setNome(atualizaProdutoRequest.getNome());
        produtoExistente.setMarca(atualizaProdutoRequest.getMarca());
        produtoExistente.setPreco(atualizaProdutoRequest.getPreco());
        produtoExistente.setEstoque(atualizaProdutoRequest.getEstoque());
        produtoExistente.setDescricao(atualizaProdutoRequest.getDescricao());

        Categoria categoria = categoriaRepository.findByNome(atualizaProdutoRequest.getCategoria().getNome());

        produtoExistente.setCategoria(categoria);
        return produtoExistente;
    }

    @Override
    public List<Produto> retornaTodosProdutos() {
        return produtoRepository.findAll();
    }

    @Override
    public List<Produto> retornaProdutosPorCategoria(String categoria) {
        return produtoRepository.findByCategoriaNome(categoria);
    }

    @Override
    public List<Produto> retornaProdutosPorMarca(String marca) {
        return produtoRepository.findByMarca(marca);
    }

    @Override
    public List<Produto> retornaProdutosPorCategoriaEMarca(String categoria, String marca) {
        return produtoRepository.findByCategoriaNomeAndMarca(categoria, marca);
    }

    @Override
    public List<Produto> retornaProdutosPorNome(String nome) {
        return produtoRepository.findByNome(nome);
    }

    @Override
    public List<Produto> retornaProdutosPorMarcaENome(String marca, String nome) {
        return produtoRepository.findByMarcaAndNome(marca, nome);
    }

    @Override
    public Long contarProdutosPorMarcaENome(String marca, String nome) {
        return produtoRepository.countByMarcaAndNome(marca, nome);
    }

    @Override
    public List<ProdutoDTO> retornaProdutosConvertidos(List<Produto> produtos) {
        return produtos.stream().map(this::convertParaDTO).toList();
    }

    @Override
    public ProdutoDTO convertParaDTO(Produto produto) {
        ProdutoDTO produtoDTO = modelMapper.map(produto, ProdutoDTO.class);
        List<Imagem> imagens = imagemRepository.findByProdutoId(produto.getId());

        List<ImagemDTO> imagensDTOs = imagens.stream().map(imagem -> modelMapper.map(imagem, ImagemDTO.class)).toList();

        produtoDTO.setImagens(imagensDTOs);
        return produtoDTO;
    }

}
