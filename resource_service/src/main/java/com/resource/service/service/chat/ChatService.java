package com.resource.service.service.chat;

import com.resource.service.exceptions.EntityNotFoundException;
import com.resource.service.model.Chat;
import com.resource.service.repository.chat.ChatRepository;
import com.resource.service.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService extends ChatAbstractService<Chat, String>{
    private final GridFsTemplate gridFsTemplate;
    private final FileService service;

    @Autowired
    public ChatService(ChatRepository repository, GridFsTemplate gridFsTemplate, FileService service) {
        super(repository);
        this.gridFsTemplate = gridFsTemplate;
        this.service = service;
    }

    @Override
    public List<Chat> findAllByUserId(String userId) {
        return repository.findChatsByUserId(userId);
    }

    @Override
    public Chat save(Chat entity) {
        entity.setAddedData(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return repository.save(entity);
    }

    @Override
    public Chat update(String idMessage, Chat entity) {
        Optional<Chat> chat = Optional.ofNullable(repository.findChatById(idMessage));
        if(chat.isPresent()){
            Chat newChat = chat.get();
            newChat.setMessage(entity.getMessage());
            newChat.setAddedData(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return repository.save(newChat);
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами: ", idMessage);
    }

    @Override
    public void delete(String id) {
        this.gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
        repository.deleteById(id);
    }
}