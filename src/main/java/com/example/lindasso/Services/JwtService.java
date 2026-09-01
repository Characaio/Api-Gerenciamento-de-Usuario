package com.example.lindasso.Services;


import com.example.lindasso.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

    @Autowired
    private JwtEncoder jwtEncoder;


    public String generateToken(Usuario usuario){

        Instant agora = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(usuario.getEmail())
                .claim("id", usuario.getId())
                .issuedAt(agora)
                .expiresAt(
                        agora.plus(1, ChronoUnit.HOURS)
                )
                .build();

        return jwtEncoder
                .encode(
                        JwtEncoderParameters.from(claims)
                )
                .getTokenValue();
    }
}
