package com.resource.service.service.task_response;

import com.resource.service.repository.EntityRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public abstract class TaskResponseAbstractService<T, ID extends Serializable> implements TaskResponseServiceInterface<T, ID> {
    private EntityRepository<T, ID> repository;

    @Override
    public void saveTaskResponse(T entity, List<MultipartFile> multipartFile) throws IOException {
        repository.save(entity);
    }

    @Override
    public T findResponseTaskByTaskId(ID id) {
        return (T) repository.findById(id);
    }

    @Override
    public void deleteResponseTaskByTaskId(ID id) {
        repository.deleteById(id);
    }
}
