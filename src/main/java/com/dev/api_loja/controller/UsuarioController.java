package com.dev.api_loja.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.api_loja.dto.UsuarioDTO;
import com.dev.api_loja.exception.RecursoNaoEncontradoExcecao;
import com.dev.api_loja.exception.UsuarioExistenteExcecao;
import com.dev.api_loja.model.Usuario;
import com.dev.api_loja.requisicao.AddUsuarioRequest;
import com.dev.api_loja.requisicao.AtualizaUsuarioRequest;
import com.dev.api_loja.resposta.ApiResponse;
import com.dev.api_loja.service.usuario.IUsuarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    @GetMapping("/{usuarioId}/usuario")
    public ResponseEntity<ApiResponse> retornaUsuarioPorId(@PathVariable Long usuarioId) {

        try {

            Usuario usuario = usuarioService.retornaUsuarioPorId(usuarioId);
            UsuarioDTO usuarioDTO = usuarioService.convertParaDTO(usuario);

            return ResponseEntity.ok(new ApiResponse("Sucesso!", usuarioDTO));
        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ApiResponse> criarUsuario(@RequestBody AddUsuarioRequest usuarioRequest) {

        try {
            Usuario usuario = usuarioService.criaUsuario(usuarioRequest);
            UsuarioDTO usuarioDTO = usuarioService.convertParaDTO(usuario);
            return ResponseEntity.ok(new ApiResponse("Usuario criado com sucesso!", usuarioDTO));
        } catch (UsuarioExistenteExcecao e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/{usuarioId}/alterar")
    public ResponseEntity<ApiResponse> alteraUsuario(@RequestBody AtualizaUsuarioRequest usuarioRequest,
            @PathVariable Long usuarioId) {

        try {

            Usuario usuario = usuarioService.alteraUsuario(usuarioRequest, usuarioId);
            UsuarioDTO usuarioDTO = usuarioService.convertParaDTO(usuario);
            return ResponseEntity.ok(new ApiResponse("Usuario alterado com sucesso!", usuarioDTO));

        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/{usuarioId}/excluir")
    public ResponseEntity<ApiResponse> excluiUsuario(@PathVariable Long usuarioId) {

        try {

            usuarioService.excluiUsuario(usuarioId);
            return ResponseEntity.ok(new ApiResponse("Usuario excluido com sucesso!", null));

        } catch (RecursoNaoEncontradoExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
}
