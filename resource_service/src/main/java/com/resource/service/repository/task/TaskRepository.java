package com.resource.service.repository.task;

import com.resource.service.model.Task;
import com.resource.service.repository.EntityRepository;

import java.util.List;

public interface TaskRepository extends EntityRepository<Task, String> {
    Task findTaskById(String id);
    List<Task> findAllByUserId(String userId);
}
