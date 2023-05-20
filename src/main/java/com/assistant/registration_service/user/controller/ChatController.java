package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.resource_service.Chat;
import com.assistant.registration_service.user.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

@RequestMapping("api/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService service;

    @PostMapping(value = "/create")
    public Chat save(@RequestBody Chat chat){
        return service.save(chat);
    }

    @PutMapping(value = "/update/{idMessage}")
    public Chat update(@PathVariable("idMessage") String idMessage,@RequestBody Chat chat){
        return service.update(idMessage, chat);
    }

    @GetMapping(value = "/get/{userId}")
    public List<Chat> get(@PathVariable("userId") String userId) {
        return service.get(userId);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }

}
