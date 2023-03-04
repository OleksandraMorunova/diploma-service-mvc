package com.assistant.api.auth.component;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtTokenProvider jwtProvider;

    public AuthenticationManager(JwtTokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String jwt = authentication.getCredentials().toString();
        String username = jwtProvider.getAccessClaims(jwt).getSubject();
        return Mono.just(jwtProvider.validateAccessToken(jwt))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = jwtProvider.getAccessClaims(jwt);
                    Set<String> roles = claims.get("roles", Set.class);
                    return new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            roles.stream().map(menuName -> new SimpleGrantedAuthority("ROLE_" + menuName)).collect(Collectors.toList())
                    );
                });
    }
}
