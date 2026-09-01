package com.example.lindasso.Controllers;

import com.example.lindasso.DTO.UsuarioCreateDTO;
import com.example.lindasso.DTO.UsuarioLoginDTO;
import com.example.lindasso.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/oi")
    public String oi(){
        return "eu existo";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UsuarioCreateDTO signupDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(signupDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

}
