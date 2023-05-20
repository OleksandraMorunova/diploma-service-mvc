package com.assistant.registration_service.auth.component;

import com.assistant.registration_service.user.model_data.model.User;
import com.mongodb.lang.NonNull;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.*;
import java.util.Date;

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
        final Instant accessExpirationInstant = LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("status", user.getStatus());
        claims.put("role", user.getRoles());

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
        final Instant refreshExpirationInstant = LocalDateTime.now().plusWeeks(1).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getId())
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
            throw new CredentialsExpiredException("Expired jwt credentials ", exception);
        } catch (SignatureException exception) { //TODO:Replace to java.lang.SecurityException
            throw new SecurityException("Invalid signature: " + exception.getMessage(), exception);
        } catch (UnsupportedJwtException exception) {
            throw new UnsupportedJwtException("Unsupported jwt exception: " + exception.getMessage(), exception);
        } catch (MalformedJwtException exception) {
            throw new MalformedJwtException("Malformed jwt exception: " + exception.getMessage(), exception);
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

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
