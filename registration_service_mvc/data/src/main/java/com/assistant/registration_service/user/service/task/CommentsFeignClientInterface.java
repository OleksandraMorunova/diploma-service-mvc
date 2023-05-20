package com.assistant.registration_service.user.service.task;

import com.assistant.registration_service.user.model_data.model.resource_service.CommentsDto;
import com.assistant.registration_service.user.model_data.model.resource_service.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

@FeignClient(name = "RESOURCE-SERVICE-COMMENTS", url = "http://localhost:8443")
public interface CommentsFeignClientInterface {
    @PostMapping("/api/v1/comments/create/{idTask}")
    TaskDto addComment(@PathVariable("idTask") String idTask, @RequestBody CommentsDto comments);

    @PutMapping("api/v1/comments/update/{id}/{id_comment}")
    TaskDto updateComment(@PathVariable("id") String id, @PathVariable("id_comment") String idComment);

    @DeleteMapping("api/v1/comments/delete/{idTask}/{idUser}/{idComment}")
    void deleteComment(@PathVariable("idTask") String idTask, @PathVariable("idUser") String idUser, @PathVariable("idComment") String idComment);

    @GetMapping("/api/v1/comments/list")
    List<TaskDto> getListOfAllTasks();
}