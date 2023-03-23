package com.assistant.registration_service.auth.service;

import com.assistant.registration_service.auth.component.CustomUserDetailService;
import com.assistant.registration_service.auth.component.JwtTokenProvider;
import com.assistant.registration_service.auth.domain.AccessJwtToken;
import com.assistant.registration_service.auth.domain.JwtRequest;
import com.assistant.registration_service.auth.domain.JwtResponse;
import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.repository.UserEntityRepository;
import com.assistant.registration_service.user.service.EncodedData;
import com.assistant.registration_service.user.service.user.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserService service;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtTokenProvider jwtProvider;
    private final CustomUserDetailService userDetailService;
    private final UserEntityRepository repository;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final User user = service.findUserByEmail(authRequest.getEmail());
        if (EncodedData.decoded(user.getPassword()).equals(authRequest.getPassword())) {
          return generateAccessAndRefreshTokens(user);
        } else {
            throw new AuthException("Invalid password.");
        }
    }

    public Collection<?> validatedToken(@NonNull AccessJwtToken token) throws AuthException {
        if(jwtProvider.validateAccessToken(token.getAccessToken())){
            Claims claims = jwtProvider.getAccessClaims(token.getAccessToken());
            UserDetails userDetails = userDetailService.loadUserByUsername(claims.getSubject());
            return userDetails.getAuthorities();
        } else throw new AuthException("Forbidden access for token " + token.getAccessToken());
    }

    public JwtResponse getRefreshToken(@NonNull String refreshToken) throws AuthException {
        final String saveRefreshToken = validateRefreshToken(refreshToken).entrySet().iterator().next().getKey();
        User user = validateRefreshToken(refreshToken).get(saveRefreshToken);
        if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
            return generateAccessAndRefreshTokens(user);
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        final String saveRefreshToken = validateRefreshToken(refreshToken).entrySet().iterator().next().getKey();
        User user = validateRefreshToken(refreshToken).get(saveRefreshToken);
        System.out.println(saveRefreshToken);
        if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)){
            final String accessToken = jwtProvider.generateAccessToken(user);
            return new JwtResponse(accessToken, null);
        } else {
            throw new AuthException("JWT token is invalid.");
        }
    }

    private JwtResponse generateAccessAndRefreshTokens(User user){
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getEmail(), refreshToken);
        user.setToken(refreshStorage);
        repository.save(user);
        return new JwtResponse(accessToken, refreshToken);
    }

    private Map<String, User> validateRefreshToken(String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final Optional<User> user = Optional.ofNullable(service.findUserByEmail(EncodedData.decoded(login)));
            if (user.isPresent()) {
                Map<String, User> map = new HashMap<>();
                map.put(user.get().getToken().get(login), user.get());
                return map;
            }
        } throw new AuthException("JWT signature does not match locally computed signature or JWT expired and it rejected.");
    }
}