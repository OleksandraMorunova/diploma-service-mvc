package com.assistant.registration_service.user.service.resource;

import com.assistant.registration_service.user.model_data.model.resource_service.ResponseTask;
import com.assistant.registration_service.user.model_data.model.resource_service.TaskDto;
import com.assistant.registration_service.user.service.task.CommentsFeignClientInterface;
import com.assistant.registration_service.user.service.task.TaskFeignClientInterface;
import com.assistant.registration_service.user.service.task.TaskResponseFeignClientsInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TasksService extends TasksAndCommentsAbstractService<TaskDto, String>{
    private final CommentsFeignClientInterface clientInterface;
    private final TaskFeignClientInterface taskFeignClientInterface;
    private final TaskResponseFeignClientsInterface taskResponseFeignClientsInterface;

    @Override
    public List<TaskDto> getListOfAllTasks(){
        return clientInterface.getListOfAllTasks();
    }

    public void saveTask(TaskDto task,  List<MultipartFile> multipartFile) {
        taskFeignClientInterface.save(task, multipartFile);
    }

    public TaskDto updateTask(String idTask, TaskDto task, List<MultipartFile> multipartFile) {
        return taskFeignClientInterface.update(idTask, task, multipartFile);
    }

    public TaskDto getTaskByIdTask(String idTask){
        return taskFeignClientInterface.getTaskByIdTask(idTask);
    }

    public void deleteTask(String idTask){
        taskFeignClientInterface.delete(idTask);
    }

    public ResponseTask findResponseTaskByTaskId(String idTask){
        return taskResponseFeignClientsInterface.findResponseTaskByTaskId(idTask);
    }

    public void createResponseTask(ResponseTask responseTask, List<MultipartFile> multipartFiles){
        taskResponseFeignClientsInterface.saveTaskResponse(responseTask, multipartFiles);
    }

    public void deleteResponseTaskById(String idTask){
        taskResponseFeignClientsInterface.deleteTaskResponse(idTask);
    }
}