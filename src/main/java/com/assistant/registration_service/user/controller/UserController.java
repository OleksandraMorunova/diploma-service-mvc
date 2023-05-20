package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.model_data.model.resource_service.UserAndTasks;
import com.assistant.registration_service.user.service.user.UserService;
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

@RequestMapping("api/content")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/user/{email}")
    public UserAndTasks findUserDetails(@Valid @PathVariable("email") @Email @NotBlank(message = "Email may not be empty") String email) {
        return service.getUser(email);
    }

    @GetMapping("/check/email/{email}")
    public User checkUserEmail(@Valid @PathVariable("email") @Email @NotBlank(message = "Email may not be empty") String email){
        return service.findUserByEmail(email);
    }

    @GetMapping("/check/id/{id}")
    public User checkUserId(@Valid @PathVariable("id") @NotBlank(message = "Email may not be empty") String email){
        return service.findById(email);
    }


    @PutMapping("/update/{phone}")
    public User updateUserDetailsByPhone(@Valid @PathVariable("phone") @NotBlank(message = "Phone may not be empty") @Size(min = 4, max = 15) String phone,
                                         @Valid @RequestPart(value = "json") User user,
                                         @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        return service.updateUser(phone, user, multipartFile);
    }

    @GetMapping("/check/phone/{phone}")
    public User checkUserPhone(@Valid @PathVariable("phone") @NotBlank(message = "Phone may not be empty") String phone){
        return service.findUserByPhone(phone);
    }
}