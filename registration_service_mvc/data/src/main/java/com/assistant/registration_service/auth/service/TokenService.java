package com.assistant.registration_service.auth.service;

import com.assistant.registration_service.auth.component.CustomUserDetailService;
import com.assistant.registration_service.auth.component.JwtTokenProvider;
import com.assistant.registration_service.auth.domain.AccessJwtToken;
import com.assistant.registration_service.auth.domain.JwtRequest;
import com.assistant.registration_service.auth.domain.JwtResponse;
import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.repository.UserEntityRepository;
import com.assistant.registration_service.user.service.user.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserService service;
    private final JwtTokenProvider jwtProvider;
    private final CustomUserDetailService userDetailService;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException, IOException {
        final User user = service.findUserByEmail(authRequest.getEmail());
        if (PasswordEncoder.check(authRequest.getPassword(), user.getPassword())) {
          return generateAccessAndRefreshTokens(user);
        } else {
            throw new AuthException("Invalid password.");
        }
    }

    public JwtResponse getRefreshToken(@NonNull String refreshToken) throws AuthException, IOException {
        final String saveRefreshToken = validateRefreshToken(refreshToken).entrySet().iterator().next().getKey();
        User user = validateRefreshToken(refreshToken).get(saveRefreshToken);
        System.out.println(saveRefreshToken);
        System.out.println(refreshToken);
        if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
            return generateAccessAndRefreshTokens(user);
        } else {
            throw new AuthException("JWT token is invalid.");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        final String saveRefreshToken = validateRefreshToken(refreshToken).entrySet().iterator().next().getKey();
        User user = validateRefreshToken(refreshToken).get(saveRefreshToken);
        if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)){
            final String accessToken = jwtProvider.generateAccessToken(user);
            return new JwtResponse(accessToken, null);
        } else {
            throw new AuthException("JWT token is invalid.");
        }
    }

    private JwtResponse generateAccessAndRefreshTokens(User user) throws IOException {
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);
        User newUser = new User();
        Map<String, String> stringMap = new HashMap<>();
        JwtResponse jwt = new JwtResponse(accessToken, refreshToken);
        stringMap.put(user.getId(), jwt.getRefreshToken());
        newUser.setToken(stringMap);
        service.updateUser(user.getId(), newUser, null);
        return jwt;
    }

    private Map<String, User> validateRefreshToken(String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final Optional<User> user = Optional.ofNullable(service.findById(login));
            if (user.isPresent()) {
                Map<String, User> map = new HashMap<>();
                map.put(user.get().getToken().get(login), user.get());
                return map;
            }
        } throw new AuthException("JWT signature does not match locally computed signature or JWT expired and it rejected.");
    }
}