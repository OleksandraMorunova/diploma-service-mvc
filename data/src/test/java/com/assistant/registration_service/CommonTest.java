package com.assistant.registration_service;

import com.assistant.registration_service.user.data.enums.Role;
import com.assistant.registration_service.user.data.enums.UserStatus;
import com.assistant.registration_service.user.data.model.User;
import com.assistant.registration_service.user.data.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonTest {

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void testModelMapperToEntity() {
        UserDTO dto = new UserDTO(
                "123",
                "Jon",
                "0987654321",
                "jon@gmail.com",
                "1234",
                String.valueOf(UserStatus.DEACTIVATED)
        );

        User entity = modelMapper.map(dto, User.class);

        assertEquals(entity.getUserId(), dto.getUserId());
        assertEquals(entity.getFirstname(), dto.getFirstname());
        assertEquals(entity.getLastname(), dto.getLastname());
        assertEquals(entity.getPhone(), dto.getPhone());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getPassword(), dto.getPassword());
    }

        @Test
    public void testModelMapperToDto() {
        User entity = new User(
                null,
                "123",
                "Jon",
                "0987654321",
                "jon@gmail.com",
                "1234",
                String.valueOf(new Date(System.currentTimeMillis())),
                Collections.singleton(Role.USER),
                String.valueOf(UserStatus.DEACTIVATED)
        );
        UserDTO dto = modelMapper.map(entity, UserDTO.class);

        assertEquals(dto.getUserId(), entity.getUserId());
        assertEquals(dto.getFirstname(), entity.getFirstname());
        assertEquals(dto.getLastname(), entity.getLastname());
        assertEquals(dto.getPhone(), entity.getPhone());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getStatus(), entity.getStatus());
    }

    @Test
    public void testDecoderAndEncoder(){
        String encoder = "admin";
        encoder = Base64.getEncoder().encodeToString(encoder.getBytes());
        assertEquals(encoder, "YWRtaW4=");
        String decoder = new String(Base64.getDecoder().decode(encoder), StandardCharsets.UTF_8);
        assertEquals(decoder,"admin");
    }
}
