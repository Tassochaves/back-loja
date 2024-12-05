package com.dev.api_loja.dados;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.dev.api_loja.model.Usuario;
import com.dev.api_loja.repository.UsuarioRepository;

import org.springframework.lang.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InicializarDados implements ApplicationListener<ApplicationReadyEvent> {

    // popular a base de dados com usuários padrão na inicialização da aplicação

    private final UsuarioRepository usuarioRepository;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        criaUsuarioPadraoSeNaoExistir();
    }

    private void criaUsuarioPadraoSeNaoExistir() {

        for (int i = 1; i <= 5; i++) {
            String emailPadrao = "usuario" + i + "@dev.com";

            if (usuarioRepository.existsByEmail(emailPadrao)) {
                continue;
            }

            Usuario usuario = new Usuario();
            usuario.setPrimeiroNome("Usuario");
            usuario.setSobrenome("Dev" + i);
            usuario.setEmail(emailPadrao);
            usuario.setSenha("123456");

            usuarioRepository.save(usuario);

            System.out.println("Usuario padrao: " + i + "criado com sucesso");
        }
    }

}
