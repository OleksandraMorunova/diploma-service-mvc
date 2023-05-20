package com.resource.service.repository.chat;

import com.resource.service.model.Chat;
import com.resource.service.repository.EntityRepository;

import java.util.List;

public interface ChatRepository extends EntityRepository<Chat, String> {
    List<Chat> findChatsByUserId(String userId);
    Chat findChatById(String id);
}
