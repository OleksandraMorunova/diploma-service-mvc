package com.assistant.registration_service.user.service.chat;

import com.assistant.registration_service.user.model_data.model.resource_service.Chat;
import com.assistant.registration_service.user.service.task.ChatFeignClientsInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatFeignClientsInterface chatFeignClientsInterface;

    public Chat save(@RequestBody Chat chat){
        return chatFeignClientsInterface.save(chat);
    }

    public Chat update(@PathVariable("idMessage") String idMessage, @RequestBody Chat chat){
        return chatFeignClientsInterface.update(idMessage, chat);
    }

    public List<Chat> get(@PathVariable("userId") String userId){
        return chatFeignClientsInterface.get(userId);
    }

    public void delete(@PathVariable("id") String id){
        chatFeignClientsInterface.delete(id);
    }
}
