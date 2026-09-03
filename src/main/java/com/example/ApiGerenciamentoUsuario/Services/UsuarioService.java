package com.example.ApiGerenciamentoUsuario.Services;

import com.example.ApiGerenciamentoUsuario.DTO.UsuarioCreateDTO;
import com.example.ApiGerenciamentoUsuario.DTO.UsuarioFullResponseDTO;
import com.example.ApiGerenciamentoUsuario.Exceptions.UsuarioJaCadastrado;
import com.example.ApiGerenciamentoUsuario.Exceptions.UsuarioNaoEncontrado;
import com.example.ApiGerenciamentoUsuario.Models.Role;
import com.example.ApiGerenciamentoUsuario.Models.Usuario;
import com.example.ApiGerenciamentoUsuario.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    private UsuarioNaoEncontrado criarErroUsuario(String mensagem){
        return new UsuarioNaoEncontrado(mensagem);
    }

    public UsuarioFullResponseDTO save(UsuarioCreateDTO signupDTO){
        if (userRepo.existsByEmail(signupDTO.Email())){
            throw new UsuarioJaCadastrado("Usuario ja cadastrado");
        } else{
            Usuario user = new Usuario();
            user.setNome(signupDTO.Nome());
            user.setDataNasc(signupDTO.DataNasc());
            user.setEmail(signupDTO.Email());
            user.setSenha(passwordEncoder.encode(signupDTO.Senha()));
            user.setRole(Role.USER);

            return new UsuarioFullResponseDTO(userRepo.save(user));
        }
    }

    public List<UsuarioFullResponseDTO> findAll() { return userRepo
            .findAll()
            .stream()
            .map(user -> new UsuarioFullResponseDTO(
                    user
            ))
            .toList(); }

    public UsuarioFullResponseDTO findById(Long Id){
        Usuario user = userRepo.findById(Id)
                .orElseThrow(
                        () -> criarErroUsuario("Usuario de Id: "+ Id + " não encontrado.")
                );

        return new UsuarioFullResponseDTO(
                user
        );

    }

    public ResponseEntity<?> deleteById(Long id){
        if (userRepo.existsById(id)){
            userRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else{
            throw criarErroUsuario("Usuario de Id: "+ id+ " não encontrado.");
        }
    }

    public UsuarioFullResponseDTO putUpdate(Long id, UsuarioCreateDTO user){

        Usuario userCriado = new Usuario();

        userCriado.setNome(user.Nome());
        userCriado.setDataNasc(user.DataNasc());
        userCriado.setEmail(user.Email());
        userCriado.setSenha(passwordEncoder.encode(user.Senha()));

        return new UsuarioFullResponseDTO(userRepo.save(userCriado));
    }


}
