package com.dev.api_loja.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<RestError> lidaComAcessoNegadoException(AccessDeniedException ex) {

        RestError restError = new RestError(FORBIDDEN.toString(), "Voce nao tem permissao para este recurso");
        // String mensagem = "Voce nao tem permissao para este recurso";
        return ResponseEntity.status(FORBIDDEN).body(restError);
    }
}
