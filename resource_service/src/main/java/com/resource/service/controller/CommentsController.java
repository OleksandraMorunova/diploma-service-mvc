package com.resource.service.controller;

import com.resource.service.model.Comments;
import com.resource.service.model.Task;
import com.resource.service.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/comments")
@RestController
@Validated
@RequiredArgsConstructor
public class CommentsController {
    private final TaskService service;
    private static final String REGEX_VALID_OBJECT_ID = "^[a-fA-F0-9]{24}$";

    @PostMapping("/create/{id}")
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public Task addComment(@PathVariable("id") String id, @RequestBody Comments comments){
        return service.addCommentById(new ObjectId(id), comments);
    }

    @DeleteMapping("/delete/{idTask}/{idUser}/{idComment}")
    @Operation(summary = "Зберегти дані про користувача, якого ще немає в базі даних")
    public void deleteComment(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID) @NotBlank(message = "ID may not empty") String idTask,
                              @Valid @PathVariable("idUser") @Pattern(regexp = REGEX_VALID_OBJECT_ID) @NotBlank(message = "ID may not empty") String idUser,
                              @Valid @PathVariable("idComment") @Pattern(regexp = REGEX_VALID_OBJECT_ID) @NotBlank(message = "ID may not empty") String idComment){
        service.deleteCommentById(new ObjectId(idTask), new ObjectId(idUser), new ObjectId(idComment));
    }
}