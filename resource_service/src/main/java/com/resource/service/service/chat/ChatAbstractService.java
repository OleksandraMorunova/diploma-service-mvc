package com.resource.service.service.chat;

import com.resource.service.model.Chat;
import com.resource.service.repository.chat.ChatRepository;

import java.io.Serializable;
import java.util.List;

public class ChatAbstractService<C, ID extends Serializable> implements ChatServiceInterface<C, ID>{
    protected ChatRepository repository;

    public ChatAbstractService(ChatRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<C> findAllByUserId(ID userId) {
        return (List<C>) repository.findChatsByUserId((String) userId);
    }

    @Override
    public C save(C entity) {
        return (C) repository.save((Chat) entity);
    }

    @Override
    public C update(ID idMessage, C entity) {
        return (C) repository.save((Chat) entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById((String) id);
    }
}
