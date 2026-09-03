package com.example.ApiGerenciamentoUsuario.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Usuario {

    @Enumerated(EnumType.STRING)
    private Role role;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataNasc;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    public Role getRole(){
        return role;
    }
    public void setRole(Role role){
        this.role = role;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public LocalDate getDataNasc(){
        return dataNasc;
    }
    public void setDataNasc(LocalDate dataNasc){
        this.dataNasc = dataNasc;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getSenha(){ return senha; }
    public void setSenha(String senha){ this.senha = senha;}
}
