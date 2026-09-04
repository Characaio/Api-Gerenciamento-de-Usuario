package com.example.ApiGerenciamentoUsuario.Controllers;


import com.example.ApiGerenciamentoUsuario.DTO.UsuarioCreateDTO;
import com.example.ApiGerenciamentoUsuario.DTO.UsuarioFullResponseDTO;
import com.example.ApiGerenciamentoUsuario.Services.UsuarioService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<UsuarioFullResponseDTO> getUsuario(Authentication authentication){

        String email = authentication.name();

        UsuarioFullResponseDTO user = usuarioService.findByEmail(email);

        return ResponseEntity.ok(user);

    }

    @GetMapping("/{id}")
    public UsuarioFullResponseDTO findById(
            @PathVariable Long id,
            Authentication authentication){

        String email = authentication.name();

        return usuarioService.findById(id,email);
    }

    @PatchMapping("/{id}")
    public UsuarioFullResponseDTO putUpdate(
            @PathVariable Long id,
            @RequestBody UsuarioCreateDTO user,
            Authentication authentication){

        String email = authentication.name();
        return usuarioService.putUpdate(id,email, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id,
            Authentication authentication){

        String email = authentication.name();

        return usuarioService.deleteById(id,email);
    }


}
