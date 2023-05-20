package com.assistant.registration_service.user.service.resource;

import com.assistant.registration_service.user.model_data.model.resource_service.UserAndTasks;

import java.io.Serializable;
import java.util.List;

public interface TasksAndCommentsServiceInterface<T, ID extends Serializable> {
    List<T> getListOfAllTasks();
}
