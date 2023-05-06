package com.resource.service.service;

import com.resource.service.model.Comments;
import com.resource.service.repository.EntityRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public abstract class TaskAbstractService<T, ID extends Serializable> implements TaskServiceInterface<T, ID>{
    protected EntityRepository<T, ID> repository;

    public TaskAbstractService(EntityRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public void saveTask(T entity, MultipartFile multipartFile) throws IOException {
        repository.save(entity);
    }

    @Override
    public T addCommentById(ID idTask, Comments comments) {
        return repository.findTaskById(idTask);
    }

    @Override
    public T updateTaskById(ID idTask, T entity, MultipartFile multipartFile) throws IOException {
        return repository.save(entity);
    }

    @Override
    public void deleteCommentById(ID idTask, ID idUser, ID idComment) {
        repository.deleteById(idComment);
    }

    @Override
    public void deleteFileFromTaskById(ID idFiles, ID idTask) {
        repository.deleteById(idFiles);
    }

    @Override
    public void deleteTaskById(ID idTask) {
        repository.deleteById(idTask);
    }

    @Override
    public List<T> findAllTaskById(ID userId) {
        return repository.findAllByUserId(userId);
    }

}
