package com.assistant.registration_service.user.model_data.model.resource_service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    private String user_comment_id;
    private String comment;
    private String comment_added_data;
}
