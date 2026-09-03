package com.example.ApiGerenciamentoUsuario.Exceptions;

public class CredenciaisInvalidas extends RuntimeException {
    public CredenciaisInvalidas(String message) {
        super(message);
    }
}
