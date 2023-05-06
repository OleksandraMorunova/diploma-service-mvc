package com.assistant.registration_service.user.model_data.model.resource_service;

import lombok.*;
import org.bson.types.ObjectId;
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
    private ObjectId userId;
    private String title;
    private String description;
    private List<ObjectId> documents;
    private String addedData;
    private String updateData;
    private List<CommentsDto> comments;
}
