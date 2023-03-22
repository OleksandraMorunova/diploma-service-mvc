package com.assistant.registration_service.auth.component;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationManagers implements AuthenticationManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtTokenProvider jwtProvider;

    public AuthenticationManagers(JwtTokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String jwt = authentication.getCredentials().toString();
        String username = jwtProvider.getAccessClaims(jwt).getSubject();

        if(jwtProvider.validateAccessToken(jwt)){
            Claims claims = jwtProvider.getAccessClaims(jwt);
            List<String> roles = claims.get("role", List.class);
            try {
                return new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        roles.stream().map(SimpleGrantedAuthority::new).toList()
                );
            } catch (Exception e){
                logger.error(e.getMessage());
            }
            return null;
        }
        return null;
    }
}
