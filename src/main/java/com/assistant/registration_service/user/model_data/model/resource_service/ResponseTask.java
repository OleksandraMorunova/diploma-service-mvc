package com.assistant.registration_service.user.model_data.model.resource_service;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTask {
    @Id
    private String id;

    @Field("user_id")
    @NonNull
    private String userId;

    @Field("task_id")
    @NonNull
    private String taskId;

    @Field("text")
    private String text;

    @Field("files")
    private List<String> files;

    @Field("added_data")
    private String addedData = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
}
