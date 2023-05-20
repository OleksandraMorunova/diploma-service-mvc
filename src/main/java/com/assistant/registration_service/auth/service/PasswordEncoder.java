package com.assistant.registration_service.auth.service;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncoder {
    public static String encode(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean check(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
