package com.dev.api_loja.controller;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.api_loja.requisicao.LoginRequest;
import com.dev.api_loja.resposta.ApiResponse;
import com.dev.api_loja.resposta.JwtResponse;
import com.dev.api_loja.security.jwt.JwtUtils;
import com.dev.api_loja.security.usuario.UsuarioLojaDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UsuarioLojaDetails usuarioDetails = (UsuarioLojaDetails) authentication.getPrincipal();
            String jwt = jwtUtils.gerarTokenParaUsuario(authentication);

            JwtResponse jwtResponse = new JwtResponse(usuarioDetails.getId(), jwt);

            return ResponseEntity.ok(new ApiResponse("Sucesso no login", jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
