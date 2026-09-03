package com.example.ApiGerenciamentoUsuario.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY = "coisa-muito-segura-muito-pika-pikachu-chuchu";

    public String generateToken(UserDetails userDetails){

        String role = userDetails.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + 1000*60*60)
                )
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey(){
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String extractUsername(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
