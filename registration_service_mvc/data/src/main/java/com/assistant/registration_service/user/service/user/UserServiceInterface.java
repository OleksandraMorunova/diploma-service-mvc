package com.assistant.registration_service.user.service.user;


import java.io.Serializable;

public interface UserServiceInterface<T, ID extends Serializable> {
    T saveUser(T entity);
    T delete(ID email);
    T findUserByEmail(ID email);
    T findUserByPhone(ID phoneNumber);
    T findAllByStatusAndRolesOrderByName(ID status);
    T findUserByEmailOrPhoneAndStatus(ID value);
}
