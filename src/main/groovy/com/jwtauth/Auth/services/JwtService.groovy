package com.jwtauth.Auth.services

import com.jwtauth.Auth.models.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service

import javax.crypto.SecretKey
import java.util.function.Function

@Service
class JwtService {

    final String SECRET_KEY = System.getenv("JWT_SECRET_KEY")

    String extractUsername(String token) {
        return extractClaim(token, Claims.&getSubject)
    }

    boolean isValid(String token, User user) {
        String username = extractUsername(token)
        return (user.username == username) && !isTokenExpired(token)
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationTime(token).before(new Date())
    }

    private Date extractExpirationTime(String token) {
        return extractClaim(token, Claims.&getExpiration)
    }

    <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token)
        return resolver.apply(claims)
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .payload
    }

    String generateToken(User user) {
        String token = Jwts
                .builder()
                .subject(user.username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(signingKey)
                .compact()
        return token
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

}
