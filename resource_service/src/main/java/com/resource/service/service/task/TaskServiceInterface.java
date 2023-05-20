package com.resource.service.service.task;

import com.resource.service.model.Comments;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface TaskServiceInterface <T, ID extends Serializable>{
    void saveTask(T entity, List<MultipartFile> multipartFile) throws IOException;
    T addCommentById(ID idTask, Comments comments);
    T updateTaskById(ID idTask, T entity, List<MultipartFile> multipartFile) throws IOException;
    void deleteTaskById(ID idTask);
    T findTaskById(ID idTask);
    void deleteCommentById(ID idTask, ID idUser, ID idComment);
    void deleteFileFromTaskById(ID idFiles, ID idTask);
    List<T> findAllTaskById(ID id);
}