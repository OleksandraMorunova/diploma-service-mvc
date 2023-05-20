package com.resource.service.controller;

import com.resource.service.model.LoadFile;
import com.resource.service.service.FileService;
import com.resource.service.service.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("api/v1/files")
@RestController
@RequiredArgsConstructor
public class FilesController {
    private final TaskService service;
    private final FileService fileService;
    private static final String REGEX_VALID_OBJECT_ID = "^[a-fA-F0-9]{24}$";

    @GetMapping("/download/{idFiles}")
    public LoadFile download(@Valid @PathVariable("idFiles") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                          @NotBlank(message = "ID may not empty") String idFiles) throws IOException {
        return fileService.download(idFiles);
    }

    @DeleteMapping("/delete/{idTask}/{idFiles}")
    @Operation(summary = "Видалити файл користувача з його завдання за його ідентифікатором завдання та файлу")
    public void deleteFileFromTaskById(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                           @NotBlank(message = "ID may not empty") String idTask,
                                       @Valid @PathVariable("idFiles") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                       @NotBlank(message = "ID may not empty") String idFiles){
        service.deleteFileFromTaskById(idFiles, idTask);
    }
}