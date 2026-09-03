package com.example.ApiGerenciamentoUsuario.DTO;

import com.example.ApiGerenciamentoUsuario.Models.Usuario;

import java.time.LocalDate;
import java.time.Period;

public record UsuarioFullResponseDTO(
    Long Id,
    String Nome,
    LocalDate DataNasc,
    int Idade,
    String Email,
    String Senha,
    String role
){
    public UsuarioFullResponseDTO(Usuario user) {
        this(
                user.getId(),
                user.getNome(),
                user.getDataNasc(),
                calcularIdade(user.getDataNasc()),
                user.getEmail(),
                user.getSenha(),
                user.getRole().name()
        );
    }

    private static int calcularIdade(LocalDate dataNascimento) {
        return Period.between(
                dataNascimento,
                LocalDate.now()
        ).getYears();
    }
}
