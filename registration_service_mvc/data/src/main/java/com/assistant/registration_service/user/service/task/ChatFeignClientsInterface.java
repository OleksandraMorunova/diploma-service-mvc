package com.assistant.registration_service.user.service.task;

import com.assistant.registration_service.user.model_data.model.resource_service.Chat;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "RESOURCE-SERVICE-CHAT", url = "http://localhost:8443")
public interface ChatFeignClientsInterface {
    @PostMapping(value = "/api/v1/chat/create")
    Chat save(@RequestBody Chat chat);

    @PutMapping(value = "/api/v1/chat/update/{idMessage}")
    Chat update(@PathVariable("idMessage") String idMessage, @RequestBody Chat chat);

    @GetMapping(value = "/api/v1/chat/get/{userId}")
    List<Chat> get(@PathVariable("userId") String userId);

    @DeleteMapping(value = "/api/v1/chat/delete/{id}")
    void delete(@PathVariable("id") String id);
}