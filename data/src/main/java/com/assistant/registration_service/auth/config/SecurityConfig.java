package com.assistant.registration_service.auth.config;

import com.assistant.registration_service.auth.component.JwtAuthFilter;
import com.assistant.registration_service.auth.component.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/auth/refresh").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers("/otc/**").permitAll()
                        .requestMatchers("api/admin/**").hasRole("ROLE_ADMIN")
                        .requestMatchers("/api/content/check/**").permitAll()
                        .requestMatchers("/api/content/update/**").permitAll()
                        .requestMatchers("api/content/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
