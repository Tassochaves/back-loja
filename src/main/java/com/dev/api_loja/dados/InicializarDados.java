package com.dev.api_loja.dados;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dev.api_loja.model.Role;
import com.dev.api_loja.model.Usuario;
import com.dev.api_loja.repository.RolelRepository;
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
    private final RolelRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        Set<String> rolesPadrao = Set.of("ROLE_ADMIN", "ROLE_USUARIO");

        criaUsuarioPadraoSeNaoExistir();

        criarRolePadraoSeNaoExistir(rolesPadrao);

        criaUsuarioAdminSeNaoExistir();

    }

    private void criaUsuarioAdminSeNaoExistir() {

        Role roleAdmin = roleRepository.findByNome("ROLE_ADMIN").get();
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
            usuario.setRoles(Set.of(roleAdmin));
            usuarioRepository.save(usuario);

            System.out.println("Usuario administrador: " + i + "criado com sucesso");
        }
    }

    private void criaUsuarioPadraoSeNaoExistir() {

        Role roleUsuario = roleRepository.findByNome("ROLE_USUARIO").get();

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
            usuario.setRoles(Set.of(roleUsuario));
            usuarioRepository.save(usuario);

            System.out.println("Usuario padrao: " + i + "criado com sucesso");
        }
    }

    private void criarRolePadraoSeNaoExistir(Set<String> roles) {

        roles.stream()
                .filter(role -> roleRepository.findByNome(role).isEmpty()).map(Role::new)
                .forEach(roleRepository::save);
    }

}
