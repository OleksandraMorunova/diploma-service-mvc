package com.assistant.api.auth.domain;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}
