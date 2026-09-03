package com.example.ApiGerenciamentoUsuario.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontrado.class)
    public ResponseEntity<String> usuarioNotFound(
            UsuarioNaoEncontrado exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UsuarioJaCadastrado.class)
    public ResponseEntity<String> usuarioJaCadastrado(UsuarioJaCadastrado exception){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
}
