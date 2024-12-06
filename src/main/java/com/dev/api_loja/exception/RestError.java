package com.dev.api_loja.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestError {

    String codigoErro;
    String mensagemErro;
}
