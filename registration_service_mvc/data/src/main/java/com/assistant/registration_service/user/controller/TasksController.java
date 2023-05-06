package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.model_data.model.resource_service.TaskDto;
import com.assistant.registration_service.user.model_data.model.resource_service.UserAndTasks;
import com.assistant.registration_service.user.service.resource.TasksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
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

    @GetMapping("/list")
    @Operation(summary = "Вивести всі завдання в базі данних")
    public List<TaskDto> getListOfAllTasks() {
        return service.getListOfAllTasks();
    }
}
