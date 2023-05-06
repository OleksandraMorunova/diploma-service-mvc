package com.resource.service.repository;

import com.resource.service.model.Task;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;

import java.util.List;

public interface TaskRepository extends EntityRepository<Task, ObjectId> {

}
