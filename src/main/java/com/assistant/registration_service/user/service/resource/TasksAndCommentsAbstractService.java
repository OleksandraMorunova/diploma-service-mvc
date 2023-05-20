package com.assistant.registration_service.user.service.resource;

import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.model_data.model.resource_service.UserAndTasks;
import com.assistant.registration_service.user.repository.EntityRepository;
import com.assistant.registration_service.user.repository.UserEntityRepository;
import com.assistant.registration_service.user.service.user.UserServiceInterface;

import java.io.Serializable;
import java.util.List;

public abstract class TasksAndCommentsAbstractService<T, ID extends Serializable> implements TasksAndCommentsServiceInterface<T, ID> {
    protected UserEntityRepository repository;

    @Override
    public List<T> getListOfAllTasks() {
        return null;
    }
}
