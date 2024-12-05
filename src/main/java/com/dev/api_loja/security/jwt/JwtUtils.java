package com.dev.api_loja.security.jwt;

import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.dev.api_loja.security.usuario.UsuarioLojaDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtils {

    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${auth.token.segredoJwt}")
    private String segredoJwt;

    @Value("${auth.token.experacaoEmMils}")
    private int tempoExpiracao;

    public String gerarTokenParaUsuario(Authentication authentication) {
        UsuarioLojaDetails usuarioLogado = (UsuarioLojaDetails) authentication.getPrincipal();

        List<String> papeis = usuarioLogado.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .subject(usuarioLogado.getEmail())
                .claim("id", usuarioLogado.getId())
                .claim("papeis", papeis)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + tempoExpiracao))
                .signWith(obterChaveAssinada())
                .compact();
    }

    private SecretKey obterChaveAssinada() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(segredoJwt));
    }

    public String obtemUsuarioTwtToken(String token) {

        return Jwts.parser()
                .verifyWith(obterChaveAssinada())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validarJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(obterChaveAssinada())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Assinatura JWT invalida: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Token JWT invalido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("O Token JWT expirou: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT nao suportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("A string de declaracoes JWT esta vazia: {}", e.getMessage());
        }

        return false;
    }
}
