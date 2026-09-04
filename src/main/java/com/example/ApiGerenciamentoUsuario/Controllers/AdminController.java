package com.example.ApiGerenciamentoUsuario.Controllers;

import com.example.ApiGerenciamentoUsuario.DTO.AlterarRoleDTO;
import com.example.ApiGerenciamentoUsuario.DTO.UsuarioFullResponseDTO;
import com.example.ApiGerenciamentoUsuario.Services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    private UsuarioService usuarioService;
    public AdminController(UsuarioService userService){
        this.usuarioService = userService;
    }

    @GetMapping
    public String Informacoes(){
        return  "Rota GET '/usuarios' mostra todos os usuarios\n" +
                "Rota POST '/usuarios/{id}/role' Muda a role do usuario do ID correspondente\n "+
                "Rota GET '/usuarios/{id}' mostra os dados do usuarios do ID correspondente";
    }

    @GetMapping("/usuarios")
    public List<UsuarioFullResponseDTO> findAll(){
        return usuarioService.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(usuarioService.findById(id));
    }


    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> deleteUsuario(
            @PathVariable Long id
    ){
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/usuarios/{id}/role")
    public ResponseEntity<?> mudarRole(
            @PathVariable Long id,
            @RequestBody AlterarRoleDTO role){

        return ResponseEntity.ok(usuarioService.alterarRole(id, role.role()));
    }

}
