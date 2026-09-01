package com.example.lindasso.Exceptions;

public class UsuarioJaCadastrado extends RuntimeException {
    public UsuarioJaCadastrado(String message) {
        super(message);
    }
}
