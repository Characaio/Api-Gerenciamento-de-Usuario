package com.example.ApiGerenciamentoUsuario.Repositories;

import com.example.ApiGerenciamentoUsuario.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    boolean existsByEmail(String Email);
    boolean existsBySenha(String Senha);
    Optional<Usuario> findByEmail(String Email);
}
