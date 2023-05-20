package com.resource.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Document(collection = "users_task_response")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTask {
    @Id
    private String id;

    @Field("user_id")
    @NonNull
    @Schema(description = "Номер користувача, що додав завдання")
    private String userId;

    @Field("task_id")
    @NonNull
    @Schema(description = "Номер користувача, що додав завдання")
    private String taskId;

    @Field("text")
    @Schema(description = "Опис основної частини завдання")
    private String text;

    @Field("files")
    @Schema(description = "Файли до завдання")
    private List<String> files;

    @Field("added_data")
    @Schema(description = "Дата і час додавання данних")
    private String addedData = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
}
