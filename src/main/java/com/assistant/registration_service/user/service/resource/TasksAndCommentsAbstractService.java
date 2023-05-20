package com.assistant.registration_service.user.service.resource;

import com.assistant.registration_service.user.repository.UserEntityRepository;

import java.io.Serializable;
import java.util.List;

public abstract class TasksAndCommentsAbstractService<T, ID extends Serializable> implements TasksAndCommentsServiceInterface<T, ID> {
    protected UserEntityRepository repository;

    @Override
    public List<T> getListOfAllTasks() {
        return null;
    }
}
