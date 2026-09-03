package com.example.ApiGerenciamentoUsuario.Services;

import com.example.ApiGerenciamentoUsuario.Models.Usuario;
import com.example.ApiGerenciamentoUsuario.Repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository userRepository;

    public CustomUserDetailsService(UsuarioRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Usuario Não encontrado")
                );
        return User
                .withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getRole().name())
                .build();
    }

}
