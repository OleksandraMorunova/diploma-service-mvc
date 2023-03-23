package com.assistant.registration_service.auth.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshJwtRequest {
    @NotBlank(message = "Token may not be empty")
    private String refreshToken;
}
