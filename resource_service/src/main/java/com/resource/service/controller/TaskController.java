package com.resource.service.controller;

import com.resource.service.model.Task;
import com.resource.service.service.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("api/v1/task")
@RestController
@Validated
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;
    private static final String REGEX_VALID_OBJECT_ID = "^[a-fA-F0-9]{24}$";

    @PostMapping(value = "/create")
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public void save(@RequestPart(value = "json") Task task,
                     @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) throws IOException {
        service.saveTask(task, multipartFile);
    }

    @PutMapping(value = "/update/{idTask}")
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public Task update(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                           @NotBlank(message = "ID may not empty") String idTask,
                       @RequestPart(value = "json", required = false) Task task,
                       @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) throws IOException {
        return service.updateTaskById(idTask, task, multipartFile);
    }

    @GetMapping("/list/{idUser}")
    @Operation(summary = "Отримати всі завдання користувача за його ідентифікатором користувача")
    public List<Task> listOfTasks(@Valid @PathVariable("idUser") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                      @NotBlank(message = "ID may not empty") String idUser){
        return service.findAllTaskById(idUser);
    }

    @GetMapping("/{idTask}")
    @Operation(summary = "Отримати всі завдання користувача за його ідентифікатором користувача")
    public Task getTaskByIdTask(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                           @NotBlank(message = "ID may not empty") String idTask){
        return service.findTaskById(idTask);
    }

    @DeleteMapping("/delete/{idTask}")
    @Operation(summary = "Отримати всі завдання користувача за його ідентифікатором завдання")
    public void delete(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                           @NotBlank(message = "ID may not empty") String idTask){
        service.deleteTaskById(idTask);
    }
}
