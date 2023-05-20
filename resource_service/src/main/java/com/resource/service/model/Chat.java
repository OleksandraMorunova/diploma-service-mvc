package com.resource.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users_chat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    private String id;

    @Field("user_id")
    @Schema(description = "Номер користувача, що бере участь у листуванні")
    private String userId;

    @Field("message")
    private String message;

    @Field("user_status_message")
    private String userStatusMessage;

    @Field("added_data")
    @Schema(description = "Дата і час додавання данних")
    private String addedData;
}
