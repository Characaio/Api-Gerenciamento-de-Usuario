package com.example.ApiGerenciamentoUsuario.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record UsuarioCreateDTO(
        String Nome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate DataNasc,
        String Email,
        String Senha
){

}
