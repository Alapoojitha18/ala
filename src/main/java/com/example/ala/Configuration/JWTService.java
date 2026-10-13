
        package com.example.ala.Configuration;
        import org.springframework.beans.factory.annotation.Value;
        import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    private final SecretKey secretKey;


    public JWTService(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(
                secret.trim().getBytes(java.nio.charset.StandardCharsets.UTF_8)
        );
    }


    // Generate JWT token
    public String generateToken(
            String email,
            String name,
            String role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);
        claims.put("role", role);

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(now))
                .setExpiration(
                        new Date(now + 10 * 60 * 60 * 1000L)
                )
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Read and validate JWT token
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get name from token
    public String getNameByToken(String token) {
        return getClaims(token).get("name", String.class);
    }

    // Get email from token
    public String getEmailByToken(String token) {
        return getClaims(token).getSubject();
    }

    // Get role from token
    public String getRoleByToken(String token) {
        return getClaims(token).get("role", String.class);
    }
}
