package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.resource_service.TaskDto;
import com.assistant.registration_service.user.service.resource.TasksService;
import com.assistant.registration_service.user.service.task.TaskFeignClientInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

@Tag(name = "Завдання", description = "Методи для роботи з завданнями (CRUD)")
@RequestMapping("api/tasks")
@RestController
@RequiredArgsConstructor
public class TasksController {
    private final TasksService service;
    private static final String REGEX_VALID_OBJECT_ID = "^[a-fA-F0-9]{24}$";

    @GetMapping("/list")
    @Operation(summary = "Вивести всі завдання в базі данних")
    public List<TaskDto> getListOfAllTasks() {
        return service.getListOfAllTasks();
    }

    @GetMapping("/list/{idUser}")
    @Operation(summary = "Вивести всі завдання в базі данних")
    public List<TaskDto> getListOfTask(@PathVariable("idUser") String idUser) {
        return service.getListOfAllTasks();
    }

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public void save(@RequestPart(value = "json") TaskDto task,
                     @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) {
        service.saveTask(task, multipartFile);
    }

    @PutMapping(value = "/update/{idTask}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public TaskDto updateTask(@jakarta.validation.Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                       @NotBlank(message = "ID may not empty") String idTask,
                       @RequestPart(value = "json", required = false) TaskDto task,
                       @RequestParam(value = "file", required = false) List<MultipartFile> multipartFile) {
        return service.updateTask(idTask, task, multipartFile);
    }

    @GetMapping("/{idTask}")
    @Operation(summary = "Отримати всі завдання користувача за його ідентифікатором користувача")
    public TaskDto getTaskByIdTask(@jakarta.validation.Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                @NotBlank(message = "ID may not empty") String idTask){
        return service.getTaskByIdTask(idTask);
    }

    @DeleteMapping("/delete/{idTask}")
    @Operation(summary = "Отримати всі завдання користувача за його ідентифікатором завдання")
    public void deleteTask(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                       @NotBlank(message = "ID may not empty") String idTask) {
        service.deleteTask(idTask);
    }
}