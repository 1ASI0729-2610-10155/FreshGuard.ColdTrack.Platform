package com.freshguard.coldtrack.platform.iam.infrastructure.tokens.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

/** Generates and validates signed JWT bearer tokens. */
@Service
public class JwtTokenService {
    private final SecretKey key;
    private final long expirationMs;

    public JwtTokenService(@Value("${security.jwt.secret}") String secret, @Value("${security.jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    public String generateToken(String subject) {
        var now = Instant.now();
        return Jwts.builder().subject(subject).issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMs))).signWith(key).compact();
    }

    public String extractSubject(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
