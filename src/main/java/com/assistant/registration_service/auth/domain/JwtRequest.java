package com.assistant.registration_service.auth.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JwtRequest {
    @NotBlank(message = "Email may not be empty")
    @Email
    private String email;

    @NotBlank(message = "Password may not be empty")
    @Size(min = 8, max = 16, message = "Name must be between 8 and 16 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$")
    private String password;
}