package com.assistant.api.user.controller;

import com.assistant.api.user.data.enums.Role;
import com.assistant.api.user.data.model.User;
import com.assistant.api.user.service.NewAbstractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Користувачі", description = "Методи для роботи з користувачами (CRUD)")
@RequestMapping("api/admin")
@RestController
public class AdminController {
    private final NewAbstractService service;

    @Autowired
    public AdminController(NewAbstractService service) {
        this.service = service;
    }

    //@CacheEvict(value = "users", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public Mono<ResponseEntity<?>> saveUserDetails(@Valid @RequestBody User user){
        service.save(user);
        return Mono.just(new ResponseEntity<>("User created successfully", HttpStatus.OK));
    }

    //@CacheEvict(value = "users", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    @Operation(summary = "Видалити всі дані про користувача за його електронною поштою")
    public Mono<HttpStatus> deleteUserDetails(@RequestBody String email){
        service.delete(email);
        return Mono.just(HttpStatus.OK);
    }

    //@Cacheable(value = "user")
    @GetMapping("/list/users")
    @Operation(summary = "Показати всі дані про користувачів, що існують в базі даних")
    public Flux<List<?>> showListOfAllUser(){
        return Flux.just(service.findAllByRoleOrderByFirstname(String.valueOf(Role.USER)));
    }
}
