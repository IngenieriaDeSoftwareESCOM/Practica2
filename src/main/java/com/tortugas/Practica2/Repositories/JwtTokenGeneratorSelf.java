package com.tortugas.Practica2.Repositories;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtTokenGeneratorSelf {

    public static String generateToken(String email) {
        // Create a secret key (use a more secure key in production)
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // HMAC using SHA-256

        // Set expiration time (e.g., 10 minutes from now)
        long expirationTime = 10 * 60 * 1000; // 10 minutes in milliseconds

        // Build the JWT
        String jwt = Jwts.builder()
                .setSubject(email) // Subject (typically user or client ID)
                .setIssuer("practica2") // Issuer of the token
                .setIssuedAt(new Date()) // Issue date
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Expiration
                .signWith(key) // Sign the token with the secret key
                .compact(); // Build the token as a String

        return jwt; // The generated Bearer token
    }
}
