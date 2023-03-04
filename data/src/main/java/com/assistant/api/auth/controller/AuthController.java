package com.assistant.api.auth.controller;

import com.assistant.api.auth.domain.JwtRequest;
import com.assistant.api.auth.domain.JwtResponse;
import com.assistant.api.auth.domain.RefreshJwtRequest;
import com.assistant.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("login")
    public Mono<ResponseEntity<JwtResponse>> login(@RequestBody JwtRequest authRequest) throws Exception {
        final JwtResponse token = authService.login(authRequest);
        return Mono.just(ResponseEntity.ok(token));
    }

    @PostMapping("token")
    public Mono<ResponseEntity<JwtResponse>> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return Mono.just(ResponseEntity.ok(token));
    }

    @PostMapping("refresh")
    public Mono<ResponseEntity<JwtResponse>> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws Exception {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return Mono.just(ResponseEntity.ok(token));
    }
}
