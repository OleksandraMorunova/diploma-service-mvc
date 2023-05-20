package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.LoadFile;
import com.assistant.registration_service.user.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("api/files")
@RestController
@RequiredArgsConstructor
public class FilesController {
    private final UserService service;
    private static final String REGEX_VALID_OBJECT_ID = "^[a-fA-F0-9]{24}$";

    @GetMapping("/download/{idFiles}")
    public LoadFile download(@Valid @PathVariable("idFiles") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                          @NotBlank(message = "ID may not empty") String idFiles) throws IOException {
        return service.download(idFiles);
    }

    @DeleteMapping("/delete/{idTask}/{idFiles}")
    public void deleteFileFromTaskById(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                           @NotBlank(message = "ID may not empty") String idTask,
                                       @Valid @PathVariable("idFiles") @Pattern(regexp = REGEX_VALID_OBJECT_ID)
                                       @NotBlank(message = "ID may not empty") String idFiles){
        service.deleteIconFromUserById(idFiles, idTask);
    }
}