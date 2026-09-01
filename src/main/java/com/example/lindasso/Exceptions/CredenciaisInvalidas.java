package com.example.lindasso.Exceptions;

public class CredenciaisInvalidas extends RuntimeException {
    public CredenciaisInvalidas(String message) {
        super(message);
    }
}
