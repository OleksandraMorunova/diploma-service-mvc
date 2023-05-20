package com.assistant.registration_service.user.model_data.model.resource_service;

import com.assistant.registration_service.user.model_data.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class UserAndTasks {
    private User userDto;
    private List<TaskDto> taskDto;
}
