package com.assistant.registration_service;

import com.assistant.registration_service.user.data.enums.Role;
import com.assistant.registration_service.user.data.enums.UserStatus;
import com.assistant.registration_service.user.data.model.User;
import com.assistant.registration_service.user.data.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonTest {

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void testDecoderAndEncoder(){
        String encoder = "admin";
        encoder = Base64.getEncoder().encodeToString(encoder.getBytes());
        assertEquals(encoder, "YWRtaW4=");
        String decoder = new String(Base64.getDecoder().decode(encoder), StandardCharsets.UTF_8);
        assertEquals(decoder,"admin");
    }
}
