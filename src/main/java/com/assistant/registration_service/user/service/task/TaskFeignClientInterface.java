package com.assistant.registration_service.user.service.task;

import com.assistant.registration_service.user.model_data.model.resource_service.TaskDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.JsonFormWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

@FeignClient(
        name = "RESOURCE-SERVICE-TASKS",
        url = "https://assistant-resource-service.azurewebsites.net",
        configuration = ClientConfiguration.class)
public interface TaskFeignClientInterface {
    @GetMapping("/api/v1/task/{idTask}")
    TaskDto getTaskByIdTask(@PathVariable("idTask") String idTask);

    @GetMapping("/api/v1/task/list/{idUser}")
    List<TaskDto> getListOfTasksForUserById(@PathVariable("idUser") String idUser);

    @PostMapping(value = "/api/v1/task/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    void save(@RequestPart(value = "json") TaskDto task,
                     @RequestPart(value = "file") List<MultipartFile> multipartFile);
    @PutMapping(value = "/api/v1/task/update/{idTask}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    TaskDto update(@PathVariable("idTask") String idTask,
                   @RequestPart(value = "json", required = false) TaskDto task,
                   @RequestParam(value = "file", required = false) List<MultipartFile> multipartFile);

    @DeleteMapping("/api/v1/task/delete/{idTask}")
    void delete(@Valid @PathVariable("idTask") String idTask);
}

class ClientConfiguration {
    @Bean
    public JsonFormWriter jsonFormWriter() {
        return new JsonFormWriter();
    }
}