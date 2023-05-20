package com.assistant.registration_service.user.service.task;

import com.assistant.registration_service.user.model_data.model.resource_service.ResponseTask;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "RESOURCE-SERVICE-TASKS-RESPONSE",
          url = "http://localhost:8443",
          configuration = ClientConfiguration.class)
public interface TaskResponseFeignClientsInterface {
    @GetMapping("/api/v1/response/{idTask}")
    ResponseTask findResponseTaskByTaskId(@PathVariable("idTask") String idTask);

    @PostMapping(value = "/api/v1/response/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    void saveTaskResponse(@RequestPart(value = "json") ResponseTask task,
                          @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile);

    @DeleteMapping("/api/v1/response/delete/{idTask}")
    void deleteTaskResponse(@Valid @PathVariable("idTask") String idTask);
}
