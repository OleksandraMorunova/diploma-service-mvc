package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.resource_service.ResponseDto;
import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Tag(name = "Користувачі", description = "Методи для роботи з користувачами (CRUD)")
@RequestMapping("api/content")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/user/{email}")
    @Operation(summary = "Знайти дані про користувача за його електронною поштою")
    public ResponseEntity<?> findUserDetails(@Valid @PathVariable @Email @NotBlank(message = "Email may not be empty") String email) {
        ResponseDto response = service.getUser(email);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping("/check/email/{email}")
    public User checkUserEmail(@Valid @PathVariable @Email @NotBlank(message = "Email may not be empty") String email){
        return service.findUserByEmail(email);
    }

    @PutMapping("/update/{phone}")
    @Operation(summary = "Оновити дані про користувача, який вже існує в базі даних")
    public User updateUserDetailsByPhone(@Valid @PathVariable @NotBlank(message = "Phone may not be empty") @Size(min = 4, max = 15) String phone,
                                         @Valid @RequestBody User user){
        return service.updateUser(phone, user);
    }

    @GetMapping("/check/phone/{phone}")
    @Operation(summary = "Знайти дані користувача за його номером")
    public User checkUserPhone(@Valid @PathVariable @NotBlank(message = "Phone may not be empty") String phone){
        return service.findUserByPhone(phone);
    }

    @GetMapping("/check/status/{value}")
    public User findUserByEmailOrPhoneAndStatus(@Valid @PathVariable("value") @NotBlank(message = "Phone or email may not be empty") String value){
        return service.findUserByEmailOrPhoneAndStatus(value);
    }
}
