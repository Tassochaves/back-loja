package com.dev.api_loja.service.usuario;

import org.springframework.stereotype.Service;

import com.dev.api_loja.excecoes.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.model.Usuario;
import com.dev.api_loja.repository.UsuarioRepository;
import com.dev.api_loja.requisicao.AddUsuarioRequest;
import com.dev.api_loja.requisicao.AtualizaUsuarioRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario retornaUsuarioPorId(Long usuarioId) {

        return null;
    }

    @Override
    public Usuario criaUsuario(AddUsuarioRequest usuarioRequest) {
        return null;
    }

    @Override
    public Usuario alteraUsuario(AtualizaUsuarioRequest atualizaReques, Long usuarioId) {
        return null;
    }

    @Override
    public void excluiUsuario(Long usuarioId) {

    }

}
