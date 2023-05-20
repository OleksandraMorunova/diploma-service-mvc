package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.resource_service.CommentsDto;
import com.assistant.registration_service.user.model_data.model.resource_service.TaskDto;
import com.assistant.registration_service.user.service.task.CommentsFeignClientInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

@RequestMapping("api/comments")
@RestController
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsFeignClientInterface clientInterface;
    private static final String REGEX_VALID_OBJECT_ID = "^[a-fA-F0-9]{24}$";

    @PostMapping("/create/{idTask}")
    public TaskDto addComment(@PathVariable("idTask") String idTask, @RequestBody CommentsDto comments){
        return clientInterface.addComment(idTask, comments);
    }

    @PutMapping("/update/{id}/{id_comment}")
    public TaskDto updateComment(@PathVariable("id") String id, @PathVariable("id_comment") String idComment){
        return clientInterface.updateComment(id, idComment);
    }

    @DeleteMapping("/delete/{idTask}/{idUser}/{idComment}")
    public void deleteComment(@Valid @PathVariable("idTask") @Pattern(regexp = REGEX_VALID_OBJECT_ID) @NotBlank(message = "ID may not empty") String idTask,
                              @Valid @PathVariable("idUser") @Pattern(regexp = REGEX_VALID_OBJECT_ID) @NotBlank(message = "ID may not empty") String idUser,
                              @Valid @PathVariable("idComment") @Pattern(regexp = REGEX_VALID_OBJECT_ID) @NotBlank(message = "ID may not empty") String idComment){
        clientInterface.deleteComment(idTask, idUser, idComment);
    }

    @GetMapping("/list")
    public List<TaskDto> getComment(){
        return clientInterface.getListOfAllTasks();
    }
}
