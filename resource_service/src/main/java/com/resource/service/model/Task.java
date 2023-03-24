package com.resource.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Document(collection = "users_task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private String id;

    @Field("user_id")
    @Schema(description = "Номер користувача, що додав завдання")
    private ObjectId userId;

    @Field("title")
    @Schema(description = "Опис заголовку завдання")
    private String title;

    @Field("description")
    @Schema(description = "Опис основної частини завдання")
    private String description;

    @Field("files")
    @Schema(description = "Файли до завдання")
    private List<ObjectId> documents;

    @Field("added_data")
    @Schema(description = "Дата і час додавання данних")
    private String addedData = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    @Field("update_data")
    @Schema(description = "Дата і час оновлення данних")
    private String updateData;

    @Field("comments")
    @Schema(description = "Коментарі до завдання користувача")
    List<Comments> comments;
}
