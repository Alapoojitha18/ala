
        package com.example.ala.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTService {

    private final String secretKey =
            "kanna123456789012345678901234567890";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    secretKey.getBytes(StandardCharsets.UTF_8)
            );


    // Generate JWT Token
    public String generateToken(
            String email,
            String name,
            String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("name", name)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000L * 60 * 60 * 10
                        )
                )
                .signWith(key)
                .compact();
    }


    // Get claims from token
    public Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    // Get email
    public String getEmailByToken(String token) {

        return getClaims(token).getSubject();
    }


    // Get name
    public String getNameByToken(String token) {

        return getClaims(token)
                .get("name", String.class);
    }


    // Get role
    public String getRoleByToken(String token) {

        return getClaims(token)
                .get("role", String.class);
    }
}
