package com.app.taskmanagement.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.app.taskmanagement.exception.APIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    // Secret key for HS512, generated securely
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + 3600000); // 1-hour expiration

        // Create the JWT with the secure key
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(secretKey) // Use the generated secure key
                .compact();
        return token;
    }

    public String getEmailFromToken(String token) {
        // Parse the token with the secure key
        Jws<Claims> claims = getJwtParserBuilder()
        		.setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        return claims.getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            // Validate the token with the secure key
            token = token.trim();
            getJwtParserBuilder()
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new APIException("Token issue: " + e.getMessage() + token);
        }
    }

    private JwtParserBuilder getJwtParserBuilder() {
        return Jwts.parserBuilder().setSigningKey(secretKey);
    }
}
