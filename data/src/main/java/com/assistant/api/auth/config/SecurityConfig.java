package com.assistant.api.auth.config;

import com.assistant.api.auth.component.AuthenticationManager;
import com.assistant.api.auth.component.SecurityContextRepository;
import org.springframework.context.annotation.Bean;

import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    private final AuthenticationManager manager;
    private final SecurityContextRepository contextRepository;

    public SecurityConfig(AuthenticationManager manager, SecurityContextRepository contextRepository) {
        this.manager = manager;
        this.contextRepository = contextRepository;
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) ->
                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
                ).accessDeniedHandler((swe, e) ->
                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))
                ).and()
                .authorizeExchange()
                .pathMatchers("/api/admin/**").hasRole("ADMIN")
                .pathMatchers("/api/content/**").hasRole("USER")
                .pathMatchers("/api/auth/**").permitAll()
                .and()
                .httpBasic().disable()
                .authenticationManager(manager)
                .securityContextRepository(contextRepository);
        return http.build();
    }
}
