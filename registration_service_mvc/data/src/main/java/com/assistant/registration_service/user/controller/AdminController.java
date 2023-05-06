package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.model_data.model.resource_service.UsersAndCountTasks;
import com.assistant.registration_service.user.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;


@Tag(name = "Користувачі", description = "Методи для роботи з користувачами (CRUD)")
@RequestMapping("api/admin")
@RestController
@Validated
@RequiredArgsConstructor
public class AdminController {
    private final UserService service;

    @PostMapping("/create")
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public User saveUserDetails(@Valid @RequestPart(value = "json") User user,
                                @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        return service.saveUser(user, multipartFile);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{email}")
    @Operation(summary = "Видалити всі дані про користувача за його електронною поштою")
    public void deleteUserDetails(@Valid @PathVariable("email") @NotBlank(message = "Email may not be empty") @Email String email){
        service.delete(email);
    }

    @GetMapping("/list/users")
    @Operation(summary = "Показати всі дані про користувачів, що існують в базі даних")
    public UsersAndCountTasks showListOfAllUser(){
        return service.findAllByRolesOrderByName();
    }
}
