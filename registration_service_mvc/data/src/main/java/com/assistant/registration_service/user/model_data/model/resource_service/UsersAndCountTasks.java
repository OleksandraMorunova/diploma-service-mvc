package com.assistant.registration_service.user.model_data.model.resource_service;

import com.assistant.registration_service.user.model_data.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersAndCountTasks {
    private List<User> userList;
    private List<Integer> integerList;
}
