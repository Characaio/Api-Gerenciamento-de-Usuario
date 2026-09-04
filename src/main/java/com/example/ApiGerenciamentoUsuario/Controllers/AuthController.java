package com.example.ApiGerenciamentoUsuario.Controllers;

import com.example.ApiGerenciamentoUsuario.DTO.UsuarioCreateDTO;
import com.example.ApiGerenciamentoUsuario.DTO.UsuarioLoginDTO;
import com.example.ApiGerenciamentoUsuario.DTO.UsuarioLoginResponseDTO;
import com.example.ApiGerenciamentoUsuario.Security.JwtService;
import com.example.ApiGerenciamentoUsuario.Services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService, UsuarioService usuarioService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.Email(),
                        request.Senha()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new UsuarioLoginResponseDTO(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UsuarioCreateDTO request){
        usuarioService.save(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
