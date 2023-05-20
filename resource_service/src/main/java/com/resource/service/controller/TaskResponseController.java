package com.resource.service.controller;

import com.resource.service.model.ResponseTask;
import com.resource.service.service.task_response.TaskResponseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("api/v1/response")
@RestController
@Validated
@RequiredArgsConstructor
public class TaskResponseController {
    private final TaskResponseService service;
    private static final String REGEX_VALID_OBJECT_ID = "^[a-fA-F0-9]{24}$";

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public void saveTaskResponse(@RequestPart(value = "json") ResponseTask task,
                                  @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) throws IOException {
        service.saveTaskResponse(task, multipartFile);
    }


    @GetMapping("/{idTask}")
    @Operation(summary = "Отримати всі завдання користувача за його ідентифікатором користувача")
    public ResponseTask findResponseTaskByTaskId(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                           @NotBlank(message = "ID may not empty") String idTask){
        return service.findResponseTaskByTaskId(idTask);
    }

    @DeleteMapping("/delete/{idTask}")
    @Operation(summary = "Отримати всі завдання користувача за його ідентифікатором завдання")
    public void deleteTaskResponse(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                           @NotBlank(message = "ID may not empty") String idTask){
        service.deleteResponseTaskByTaskId(idTask);
    }
}
