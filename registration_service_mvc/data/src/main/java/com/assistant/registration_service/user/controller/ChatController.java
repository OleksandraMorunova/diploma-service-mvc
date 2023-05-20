package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.resource_service.Chat;
import com.assistant.registration_service.user.model_data.model.resource_service.CommentsDto;
import com.assistant.registration_service.user.model_data.model.resource_service.TaskDto;
import com.assistant.registration_service.user.service.chat.ChatService;
import com.assistant.registration_service.user.service.task.CommentsFeignClientInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

@Tag(name = "Чат", description = "Методи для роботи з чатом (CRUD)")
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
