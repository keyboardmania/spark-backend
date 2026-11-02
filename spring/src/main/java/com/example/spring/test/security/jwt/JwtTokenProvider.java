package com.example.spring.test.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtTokenProvider(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.expiration-ms}") long expirationMs
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    public String getUuid(String token) {
        return parseClaimsJws(token).getSubject();
    }

    public String createToken(String uuid, String role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                   .subject(uuid)
                   .claim("role", role)
                   .issuedAt(now)
                   .expiration(expiration)
                   .signWith(secretKey)
                   .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaimsJws(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
