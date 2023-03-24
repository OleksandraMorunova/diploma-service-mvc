package com.assistant.registration_service.auth.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessJwtToken {
    @NotBlank(message = "Token may not be empty")
    private String accessToken;
}
