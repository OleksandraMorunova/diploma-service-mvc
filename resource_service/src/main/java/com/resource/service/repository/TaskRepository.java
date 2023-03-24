package com.resource.service.repository;

import com.resource.service.model.Task;
import org.bson.types.ObjectId;

public interface TaskRepository extends EntityRepository<Task, ObjectId> {
}
