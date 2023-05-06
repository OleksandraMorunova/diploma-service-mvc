package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.resource_service.UserAndTasks;
import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.IOException;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

@Tag(name = "Користувачі", description = "Методи для роботи з користувачами (CRUD)")
@RequestMapping("api/content")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/user/{email}")
    @Operation(summary = "Знайти дані про користувача за його електронною поштою")
    public UserAndTasks findUserDetails(@Valid @PathVariable @Email @NotBlank(message = "Email may not be empty") String email) {
        return service.getUser(email);
    }

    @GetMapping("/check/email/{email}")
    public User checkUserEmail(@Valid @PathVariable @Email @NotBlank(message = "Email may not be empty") String email){
        return service.findUserByEmail(email);
    }

    @PutMapping("/update/{phone}")
    @Operation(summary = "Оновити дані про користувача, який вже існує в базі даних")
    public User updateUserDetailsByPhone(@Valid @PathVariable @NotBlank(message = "Phone may not be empty") @Size(min = 4, max = 15) String phone,
                                         @Valid @RequestPart(value = "json") User user,
                                         @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        return service.updateUser(phone, user, multipartFile);
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
