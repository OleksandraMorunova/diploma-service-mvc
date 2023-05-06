package com.assistant.registration_service.user.repository;

import com.assistant.registration_service.user.model_data.model.User;

import java.util.List;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */
public interface UserEntityRepository extends EntityRepository<User, String> {
    User findUserByEmail(String email);
    User findUserById(String id);
    User findUserByPhone(String phoneNumber);
    User findUserByCode(String code);
    void deleteUserByEmail(String email);
    List<User> findUsersByRolesOrderByName(String roles);
}
