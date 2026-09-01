package com.example.lindasso.DTO;

import java.time.LocalDate;

public record UsuarioCreateDTO(
        String Nome,
        LocalDate DataNasc,
        String Email,
        String Senha
){

}
