package com.resource.service.controller;

import com.resource.service.model.Task;
import com.resource.service.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public ResponseEntity<?> save(@RequestPart(value = "json") Task task, @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        service.saveTask(task, multipartFile);
        return new ResponseEntity<>("Creating new post", HttpStatus.CREATED);
    }

    @PostMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public Task update(@Valid @PathVariable("id") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                           @NotBlank(message = "ID may not empty") String id, @RequestPart(value = "json", required = false)Task task,
                       @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        return service.updateTaskById(new ObjectId(id), task, multipartFile);
    }

    @GetMapping("/list/{idUser}")
    @Operation(summary = "Отримати всі завдання користувача за його ідентифікатором користувача")
    public ResponseEntity<List<Task>> list(@Valid @PathVariable("idUser") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                      @NotBlank(message = "ID may not empty") String idUser){
        List<Task> taskList = service.findAllTaskById(new ObjectId(idUser));
        if(taskList.isEmpty()){
            return new ResponseEntity<>(taskList, HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idTask}")
    @Operation(summary = "Отримати всі завдання користувача за його ідентифікатором завдання")
    public void delete(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                           @NotBlank(message = "ID may not empty") String idTask){
        service.deleteTaskById(new ObjectId(idTask));
    }
}
