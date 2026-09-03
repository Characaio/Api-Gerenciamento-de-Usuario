package com.example.ApiGerenciamentoUsuario.Exceptions;

public class UsuarioJaCadastrado extends RuntimeException {
    public UsuarioJaCadastrado(String message) {
        super(message);
    }
}
