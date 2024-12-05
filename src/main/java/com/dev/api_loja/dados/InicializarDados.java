package com.dev.api_loja.dados;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dev.api_loja.model.Papel;
import com.dev.api_loja.model.Usuario;
import com.dev.api_loja.repository.PapelRepository;
import com.dev.api_loja.repository.UsuarioRepository;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Transactional
@Component
@RequiredArgsConstructor
public class InicializarDados implements ApplicationListener<ApplicationReadyEvent> {

    // popular a base de dados com usuários padrão na inicialização da aplicação

    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        Set<String> papeisPadrao = Set.of("PAPEL_ADMIN", "PAPEL_USUARIO");

        criaUsuarioPadraoSeNaoExistir();

        criarPapelPadraoSeNaoExistir(papeisPadrao);

        criaUsuarioAdminSeNaoExistir();

    }

    private void criaUsuarioAdminSeNaoExistir() {

        Papel pepelAdmin = papelRepository.findByNome("PAPEL_ADMIN").get();
        for (int i = 1; i <= 2; i++) {
            String emailPadrao = "admin" + i + "@dev.com";

            if (usuarioRepository.existsByEmail(emailPadrao)) {
                continue;
            }

            Usuario usuario = new Usuario();
            usuario.setPrimeiroNome("Admin");
            usuario.setSobrenome("Admin" + i);
            usuario.setEmail(emailPadrao);
            usuario.setSenha(passwordEncoder.encode("123456"));
            usuario.setPapeis(Set.of(pepelAdmin));
            usuarioRepository.save(usuario);

            System.out.println("Usuario administrador: " + i + "criado com sucesso");
        }
    }

    private void criaUsuarioPadraoSeNaoExistir() {

        Papel pepelUsuario = papelRepository.findByNome("PAPEL_USUARIO").get();

        for (int i = 1; i <= 5; i++) {
            String emailPadrao = "usuario" + i + "@dev.com";

            if (usuarioRepository.existsByEmail(emailPadrao)) {
                continue;
            }

            Usuario usuario = new Usuario();
            usuario.setPrimeiroNome("Usuario");
            usuario.setSobrenome("usuario" + i);
            usuario.setEmail(emailPadrao);
            usuario.setSenha(passwordEncoder.encode("123456"));
            usuario.setPapeis(Set.of(pepelUsuario));
            usuarioRepository.save(usuario);

            System.out.println("Usuario padrao: " + i + "criado com sucesso");
        }
    }

    private void criarPapelPadraoSeNaoExistir(Set<String> papeis) {

        papeis.stream()
                .filter(papel -> papelRepository.findByNome(papel).isEmpty()).map(Papel::new)
                .forEach(papelRepository::save);
    }

}
