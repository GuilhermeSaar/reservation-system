package com.gstech.reservationSystem.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gstech.reservationSystem.orm.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class JWTService {

    @Value("${api.security.token.secret}")
    private String secret;


    public String generateToken(User user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(tokenExpirationTime())
                    .withClaim("role", user.getRole().getRole())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error generating token");
        }
    }

    public Optional<String> validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return Optional.of(JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject()
            );
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }

    private Instant tokenExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
