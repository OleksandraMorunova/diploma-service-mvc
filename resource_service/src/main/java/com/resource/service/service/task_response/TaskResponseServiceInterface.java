package com.resource.service.service.task_response;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface TaskResponseServiceInterface<T, ID extends Serializable>{
    void saveTaskResponse(T entity, List<MultipartFile> multipartFile) throws IOException;
    T findResponseTaskByTaskId(ID id);
    void deleteResponseTaskByTaskId(ID id);
}
