package com.assistant.registration_service.auth.service;

import com.assistant.registration_service.auth.component.CustomUserDetailService;
import com.assistant.registration_service.auth.component.JwtTokenProvider;
import com.assistant.registration_service.auth.domain.AccessJwtToken;
import com.assistant.registration_service.auth.domain.JwtRequest;
import com.assistant.registration_service.auth.domain.JwtResponse;
import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.service.user.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserService service;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtTokenProvider jwtProvider;
    private final CustomUserDetailService userDetailService;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final User user = service.findUserByEmail(authRequest.getEmail());
        if ((new String(Base64.getDecoder().decode(user.getPassword()), StandardCharsets.UTF_8)).equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Invalid password");
        }
    }

    public Collection<?> validatedToken(AccessJwtToken token) throws AuthException {
        if(jwtProvider.validateAccessToken(token.getToken())){
            Claims claims = jwtProvider.getAccessClaims(token.getToken());
            UserDetails userDetails = userDetailService.loadUserByUsername(claims.getSubject());
            return userDetails.getAuthorities();
        } else throw new AuthException("Forbidden access" + token.getToken());
    }

    public JwtResponse getRefreshToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = service.findUserByEmail(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }


    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            System.out.println(saveRefreshToken);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = service.findUserByEmail(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            } throw new AuthException("Token isn't valid");
        }
        return new JwtResponse(null, null);
    }
}
