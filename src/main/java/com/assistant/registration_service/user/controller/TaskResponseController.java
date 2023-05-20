package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.resource_service.ResponseTask;
import com.assistant.registration_service.user.service.resource.TasksService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("api/response")
@RestController
@RequiredArgsConstructor
public class TaskResponseController {
    private final TasksService service;
    private static final String REGEX_VALID_OBJECT_ID = "^[a-fA-F0-9]{24}$";

    @GetMapping("/{idTask}")
    public ResponseTask findResponseTaskByTaskId(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                                 @NotBlank(message = "ID may not empty") String idTask) {
        return service.findResponseTaskByTaskId(idTask);
    }

    @PostMapping(value = "/create")
    public void saveTaskResponse(@RequestPart(value = "json") ResponseTask task,
                                 @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) {
        service.createResponseTask(task, multipartFile);
    }

    @DeleteMapping("/delete/{idTask}")
    public void deleteTaskResponse(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                   @NotBlank(message = "ID may not empty") String idTask) {
        service.deleteResponseTaskById(idTask);
    }
}
