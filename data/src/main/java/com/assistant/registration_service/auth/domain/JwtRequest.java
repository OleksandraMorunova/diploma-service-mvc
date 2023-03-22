package com.assistant.registration_service.auth.domain;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}
