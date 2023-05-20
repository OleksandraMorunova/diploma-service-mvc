package com.assistant.registration_service.user.service.user;

import com.assistant.registration_service.user.model_data.model.User;

import java.io.Serializable;


public interface UserServiceInterface<T, ID extends Serializable> {
    void delete(String email);
    User findUserByEmail(String email);
    User findUserByPhone(String phoneNumber);
}
