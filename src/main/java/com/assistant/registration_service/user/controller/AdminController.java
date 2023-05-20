package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.model_data.model.resource_service.UsersAndCountTasks;
import com.assistant.registration_service.user.service.user.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;


@RequestMapping("api/admin")
@RestController
@Validated
@RequiredArgsConstructor
public class AdminController {
    private final UserService service;

    @PostMapping("/create")
    public User saveUserDetails(@Valid @RequestPart(value = "json") User user,
                                @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        return service.saveUser(user, multipartFile);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deleteUserDetails(@Valid @PathVariable("id") @NotBlank(message = "Email may not be empty") String id){
        service.delete(id);
    }

    @GetMapping("/list/users")
    public UsersAndCountTasks showListOfAllUser(){
        return service.findAll();
    }
}
