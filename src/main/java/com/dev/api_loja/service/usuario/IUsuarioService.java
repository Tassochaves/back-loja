package com.dev.api_loja.service.usuario;

import com.dev.api_loja.model.Usuario;
import com.dev.api_loja.requisicao.AddUsuarioRequest;
import com.dev.api_loja.requisicao.AtualizaUsuarioRequest;

public interface IUsuarioService {

    Usuario retornaUsuarioPorId(Long usuarioId);

    Usuario criaUsuario(AddUsuarioRequest usuarioRequest);

    Usuario alteraUsuario(AtualizaUsuarioRequest atualizaReques, Long usuarioId);

    void excluiUsuario(Long usuarioId);
}
