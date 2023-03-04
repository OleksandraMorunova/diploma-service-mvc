package com.assistant.api.user.controller;

import com.assistant.api.user.data.model.User;
import com.assistant.api.user.service.NewAbstractService;
import com.assistant.api.user.service.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Tag(name = "Користувачі", description = "Методи для роботи з користувачами (CRUD)")
@RequestMapping("api/content")
@RestController
public class UserController {
    private final NewAbstractService service;
    @Autowired
    public UserController(NewAbstractService service) {
        this.service = service;
    }

    //@Cacheable(value = "users", key = "#user.email")
    @GetMapping("/user/{email}")
    @Operation(summary = "Знайти дані про користувача за його електронною поштою")
    public Mono<Optional<?>> findUserDetails(@PathVariable String email){
        return Mono.just(Optional.ofNullable(service.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Record not found with id : " + email))));
    }

    //@CacheEvict(value = "users", allEntries = true)
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/{email}")
    @Operation(summary = "Оновити дані про користувача, який вже існує в базі даних")
    public Mono<ResponseEntity<?>> updateUserDetails(@PathVariable String email, @RequestBody User user){
        user.setEmail(email);
        return Mono.just(ResponseEntity.ok().body(this.service.update(user)));
    }

    @CacheEvict(value = "users", allEntries = true)
    @GetMapping("/delete/cache")
    public String clearCache(){
        return "Clear cache";
    }
}
