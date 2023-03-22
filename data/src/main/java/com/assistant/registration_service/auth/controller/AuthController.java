package com.assistant.registration_service.auth.controller;

import com.assistant.registration_service.auth.domain.AccessJwtToken;
import com.assistant.registration_service.auth.domain.JwtRequest;
import com.assistant.registration_service.auth.domain.JwtResponse;
import com.assistant.registration_service.auth.domain.RefreshJwtRequest;
import com.assistant.registration_service.auth.service.TokenService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException{
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException{
        final JwtResponse token = authService.getRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/valid/token")
    public ResponseEntity<?> validatedToken(@RequestBody AccessJwtToken token) throws AuthException {
        Collection<?> collection = authService.validatedToken(token);
        if(collection.isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else return new ResponseEntity<>(collection, HttpStatus.OK);
    }
}
