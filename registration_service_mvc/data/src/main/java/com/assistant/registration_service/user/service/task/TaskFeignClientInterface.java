package com.assistant.registration_service.user.service.task;

import com.assistant.registration_service.user.model_data.model.resource_service.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

@FeignClient(name = "RESOURCE-SERVICE", url = "http://localhost:8443")
public interface TaskFeignClientInterface {

    @GetMapping("/api/v1/task/list/{idUser}")
    ResponseEntity<List<TaskDto>> getListOfTasksForUserById(@PathVariable("idUser") String idUser);

    @GetMapping("/api/v1/comments/list")
    List<TaskDto> getListOfAllTasks();
}

