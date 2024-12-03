package com.dev.api_loja.security.usuario;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dev.api_loja.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLojaDetails implements UserDetails {

    private Long id;
    private String email;
    private String senha;

    private Collection<GrantedAuthority> autoridades;

    public static UsuarioLojaDetails criarUsuarioDetails(Usuario usuario) {

        List<GrantedAuthority> listaAutoridades = usuario.getPapeis().stream()
                .map(papel -> new SimpleGrantedAuthority(papel.getNome())).collect(Collectors.toList());

        return new UsuarioLojaDetails(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getSenha(),
                listaAutoridades);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return autoridades;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
