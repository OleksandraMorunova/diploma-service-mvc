package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.resource_service.ResponseDto;
import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Користувачі", description = "Методи для роботи з користувачами (CRUD)")
@RequestMapping("api/content")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/user/{email}")
    @Operation(summary = "Знайти дані про користувача за його електронною поштою")
    public ResponseEntity<?> findUserDetails(@PathVariable String email) {
        ResponseDto response = service.getUser(email);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping("/check/email/{email}")
    public User checkUserEmail(@PathVariable String email){
        return service.findUserByEmail(email);
    }

    @PutMapping("/update/{phone}")
    @Operation(summary = "Оновити дані про користувача, який вже існує в базі даних")
    public User updateUserDetailsByPhone(@PathVariable String phone, @RequestBody User user){
        return service.update(phone, user);
    }

    @GetMapping("/check/phone/{phone}")
    @Operation(summary = "Знайти дані користувача за його номером")
    public User checkUserPhone(@PathVariable String phone){
        return service.findUserByPhone(phone);
    }

    @GetMapping("/check/status/{value}")
    public User findUserByEmailOrPhoneAndStatus(@PathVariable("value") String value){
        return service.findUserByEmailOrPhoneAndStatus(value);
    }
}
