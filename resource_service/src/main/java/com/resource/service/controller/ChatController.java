package com.resource.service.controller;

import com.resource.service.model.Chat;
import com.resource.service.service.chat.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService service;

    @PostMapping(value = "/create")
    @Operation(summary = "Зберегти запис чату")
    public Chat save(@RequestBody Chat chat){
        return service.save(chat);
    }

    @PutMapping(value = "/update/{idMessage}")
    @Operation(summary = "Зберегти запис чату")
    public Chat update(@PathVariable("idMessage") String idMessage,@RequestBody Chat chat){
        return service.update(idMessage, chat);
    }

    @GetMapping(value = "/get/{userId}")
    @Operation(summary = "Отримати дані чату за допомогою іднтифікатора двох користувачів, які безпосередньо беруть участь у спілкуванні")
    public List<Chat> get(@PathVariable("userId") String userId) {
       return service.findAllByUserId(userId);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Видалити запис з чату")
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }
}