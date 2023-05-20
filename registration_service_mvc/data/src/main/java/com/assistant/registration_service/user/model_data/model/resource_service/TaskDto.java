package com.assistant.registration_service.user.model_data.model.resource_service;

import lombok.*;

import java.util.List;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private String id;
    private String userId;
    private String title;
    private String description;
    private List<String> files;
    private String addedData;
    private String updateData;
    private List<CommentsDto> comments;
    private String audit;
}