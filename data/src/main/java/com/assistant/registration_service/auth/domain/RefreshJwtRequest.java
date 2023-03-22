package com.assistant.registration_service.auth.domain;

import lombok.Data;

@Data
public class RefreshJwtRequest {
    public String refreshToken;
}
