package com.resource.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    @Field("id")
    private String id;

    @Field("user_comment_id")
    @Schema(description = "Ідентифікатор користувача, хто відправив повідомлення")
    private String user_comment_id;

    @Field("comment")
    @Schema(description = "Текст повідомлення")
    private String comment;

    @Field("comment_added_data")
    @Schema(description = "Дата додавання коментаря")
    private String comment_added_data = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    @Field("reviewed")
    private Boolean reviewed;
}
