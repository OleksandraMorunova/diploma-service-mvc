package com.resource.service.repository.task;

import com.resource.service.model.ResponseTask;
import com.resource.service.repository.EntityRepository;

public interface TaskResponseRepository extends EntityRepository<ResponseTask, String> {
    ResponseTask findResponseTaskByTaskId(String id);
    void deleteResponseTaskByTaskId(String id);
}
