package com.assistant.registration_service.user.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

public class EncodedData {
    public static String encoded(String value){
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    public static String decoded(String value){
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }
}
