package com.assistant.registration_service.auth.controller;

import com.assistant.registration_service.auth.domain.AccessJwtToken;
import com.assistant.registration_service.auth.domain.JwtRequest;
import com.assistant.registration_service.auth.domain.JwtResponse;
import com.assistant.registration_service.auth.domain.RefreshJwtRequest;
import com.assistant.registration_service.auth.service.TokenService;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest authRequest) throws AuthException, IOException {
        final JwtResponse token = tokenService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    public ResponseEntity<?> getNewAccessToken(@Valid @RequestBody RefreshJwtRequest request) throws AuthException{
        final JwtResponse token = tokenService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> getNewRefreshToken(@Valid @RequestBody RefreshJwtRequest request) throws AuthException, IOException {
        final JwtResponse token = tokenService.getRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
