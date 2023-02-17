package com.assistant.api.auth.component;

import com.assistant.api.auth.model.JwtAuthentication;
import com.assistant.api.user.data.model.User;
import com.mongodb.lang.NonNull;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    public JwtTokenProvider(@Value("${jwt.secret.access}") String jwtAccessSecret,
                            @Value("${jwt.secret.refresh}") String jwtRefreshSecret) {
        this.jwtAccessSecret = decoders(jwtAccessSecret);
        this.jwtRefreshSecret = decoders(jwtRefreshSecret);
    }

    private SecretKey decoders(String key){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public String generateAccessToken(@NonNull User user) {
        final Instant accessExpirationInstant = LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("firstname", user.getFirstname());
        claims.put("lastname", user.getLastname());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setHeaderParam("alg","HS256")
                .setHeaderParam("typ", "JWT")
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .compact();
    }

    public String generateRefreshToken(@NonNull User user) {
        final Instant refreshExpirationInstant = LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException exception) {
            logger.error("Token expired exception", exception);
        } catch (UnsupportedJwtException exception) {
            logger.error("Unsupported jwt exception", exception);
        } catch (MalformedJwtException exception) {
            logger.error("Malformed jwt exception", exception);
        } catch (Exception exception) {
            logger.error("Token is invalid", exception);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    public JwtAuthentication getUserFromJwt(String token){
        Claims body = getAccessClaims(token);
        JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setEmail(body.getSubject());
        return jwtAuthentication;
    }
    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
