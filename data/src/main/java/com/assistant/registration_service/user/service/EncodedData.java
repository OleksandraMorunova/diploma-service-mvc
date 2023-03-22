package com.assistant.registration_service.user.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodedData {
    public String encoded(String value){
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    public String decoded(String value){
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }
}
