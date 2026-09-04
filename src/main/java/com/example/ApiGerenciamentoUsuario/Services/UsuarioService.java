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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private UsuarioNaoEncontrado criarErroUsuario(String mensagem){
        return new UsuarioNaoEncontrado(mensagem);
    }

    public UsuarioFullResponseDTO save(UsuarioCreateDTO signupDTO){
        if (usuarioRepository.existsByEmail(signupDTO.Email())){
            throw new UsuarioJaCadastrado("Usuario ja cadastrado");
        } else{
            Usuario user = new Usuario();
            user.setNome(signupDTO.Nome());
            user.setDataNasc(signupDTO.DataNasc());
            user.setEmail(signupDTO.Email());
            user.setSenha(passwordEncoder.encode(signupDTO.Senha()));
            user.setRole(Role.USER);

            return new UsuarioFullResponseDTO(usuarioRepository.save(user));
        }
    }
    
    public UsuarioFullResponseDTO findByEmail(String email){
        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(
                        () -> criarErroUsuario("Usuario de Email: "+ email + " não encontrado.")
                );
        return new UsuarioFullResponseDTO(user);

    }

    public List<UsuarioFullResponseDTO> findAll() { return usuarioRepository
            .findAll()
            .stream()
            .map(user -> new UsuarioFullResponseDTO(
                    user
            ))
            .toList();
    }

    private Usuario verificarProprietario(Long id, String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsuarioNaoEncontrado("Usuario não encontrado"));

        if (!usuario.getId().equals(id)){
            throw new AccessDeniedException("Acesso Negado");
        }

        return usuario;
    }

    public UsuarioFullResponseDTO findById(Long Id,String email){
        Usuario usuario = verificarProprietario(Id,email);

        return new UsuarioFullResponseDTO(usuario);
    }
    public UsuarioFullResponseDTO findById(Long Id){
        Usuario usuario = usuarioRepository.findById(Id)
                .orElseThrow(() -> new UsuarioNaoEncontrado("Usuario não encontrado"));

        return new UsuarioFullResponseDTO(usuario);
    }

    public ResponseEntity<?> deleteById(Long id, String email){
        Usuario usuario = verificarProprietario(id,email);

        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    public ResponseEntity<?> deleteById(Long id){
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public UsuarioFullResponseDTO putUpdate(Long id,String email, UsuarioCreateDTO user){

        Usuario userCriado = verificarProprietario(id,email);

        userCriado.setNome(user.Nome());
        userCriado.setDataNasc(user.DataNasc());
        userCriado.setEmail(user.Email());
        userCriado.setSenha(passwordEncoder.encode(user.Senha()));

        return new UsuarioFullResponseDTO(usuarioRepository.save(userCriado));
    }

    public UsuarioFullResponseDTO alterarRole(Long id, Role novaRole){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNaoEncontrado("Usuario não encontrado"));

        usuario.setRole(novaRole);

        return new UsuarioFullResponseDTO(usuarioRepository.save(usuario));
    }

}
