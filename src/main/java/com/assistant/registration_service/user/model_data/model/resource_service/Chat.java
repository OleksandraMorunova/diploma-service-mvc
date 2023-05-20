package com.assistant.registration_service.user.model_data.model.resource_service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("message")
    private String message;

    @Field("user_status_message")
    private String userStatusMessage;

    @Field("added_data")
    private String addedData;
}