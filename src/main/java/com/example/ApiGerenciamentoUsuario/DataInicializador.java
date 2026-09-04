package com.example.ApiGerenciamentoUsuario;


import com.example.ApiGerenciamentoUsuario.Models.Role;
import com.example.ApiGerenciamentoUsuario.Models.Usuario;
import com.example.ApiGerenciamentoUsuario.Repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInicializador implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInicializador(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){
        if (usuarioRepository.findByEmail("admin@gmail.com").isEmpty()) {

            Usuario admin = new Usuario();

            admin.setNome("Administrador");
            admin.setEmail("admin@gmail.com");
            admin.setDataNasc(LocalDate.of(2000,1,1));
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);

            usuarioRepository.save(admin);

            System.out.println("ADMIN padrão criado!");
        }

    }


}
