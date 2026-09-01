package com.example.lindasso.Controllers;


import com.example.lindasso.DTO.UsuarioCreateDTO;
import com.example.lindasso.DTO.UsuarioFullResponseDTO;
import com.example.lindasso.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;

    @GetMapping
    public List<UsuarioFullResponseDTO> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UsuarioFullResponseDTO findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PatchMapping("/{id}")
    public UsuarioFullResponseDTO putUpdate(@PathVariable Long id, @RequestBody UsuarioCreateDTO user){
        return userService.putUpdate(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return userService.deleteById(id);
    }
}
