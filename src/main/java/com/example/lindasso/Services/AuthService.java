package com.example.lindasso.Services;

import com.example.lindasso.DTO.*;
import com.example.lindasso.Exceptions.CredenciaisInvalidas;
import com.example.lindasso.Exceptions.UsuarioJaCadastrado;
import com.example.lindasso.Models.Usuario;
import com.example.lindasso.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository userRepo;

    @Autowired
    private JwtService jwtService;

    public UsuarioFullResponseDTO signup(UsuarioCreateDTO signupDTO){
        if (userRepo.existsByEmail(signupDTO.Email())){
            throw new UsuarioJaCadastrado("Usuario ja cadastrado");
        } else{
            Usuario user = new Usuario();
            user.setNome(signupDTO.Nome());
            user.setDataNasc(signupDTO.DataNasc());
            user.setEmail(signupDTO.Email());
            user.setSenha(passwordEncoder.encode(signupDTO.Senha()));

            return new UsuarioFullResponseDTO(userRepo.save(user));
        }
    }

    public UsuarioLoginResponseDTO login(UsuarioLoginDTO loginDTO){
        Usuario user = userRepo
                .findByEmail(loginDTO.Email())
                .orElseThrow(() -> new CredenciaisInvalidas("Login ou senha invalidos"));
        if (!passwordEncoder.matches(loginDTO.Senha(), user.getSenha())){
            throw new CredenciaisInvalidas("Login ou senha invalidos");
        }

        String token = jwtService.generateToken(user);

        return new UsuarioLoginResponseDTO(token);

    }
}
