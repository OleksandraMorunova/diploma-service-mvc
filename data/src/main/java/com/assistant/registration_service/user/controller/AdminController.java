package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Tag(name = "Користувачі", description = "Методи для роботи з користувачами (CRUD)")
@RequestMapping("api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final UserService service;

    @PostMapping("/create")
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public User saveUserDetails(@Valid @RequestBody User user){
        return service.save(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    @Operation(summary = "Видалити всі дані про користувача за його електронною поштою")
    public void deleteUserDetails(@RequestBody String email){
        service.delete(email);
    }

    @GetMapping("/list/users")
    @Operation(summary = "Показати всі дані про користувачів, що існують в базі даних")
    public User showListOfAllUser(String status){
        return service.findAllByStatusAndRolesOrderByName(status);
    }
}
