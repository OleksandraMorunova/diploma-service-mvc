package com.assistant.api.auth.domain;

import lombok.Data;

@Data
public class RefreshJwtRequest {
    public String refreshToken;
}
