package com.assistant.registration_service.user.service.resource;

import com.assistant.registration_service.user.model_data.model.resource_service.TaskDto;
import com.assistant.registration_service.user.service.task.TaskFeignClientInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TasksService extends TasksAndCommentsAbstractService<TaskDto, String>{
    private final TaskFeignClientInterface clientInterface;

    @Override
    public List<TaskDto> getListOfAllTasks(){
        return clientInterface.getListOfAllTasks();
    }
}
