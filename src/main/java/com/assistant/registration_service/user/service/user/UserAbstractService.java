package com.assistant.registration_service.user.service.user;

import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.repository.EntityRepository;
import com.assistant.registration_service.user.repository.UserEntityRepository;

import java.io.Serializable;

public abstract class UserAbstractService<T, ID extends Serializable> implements UserServiceInterface<T, ID> {
    protected EntityRepository<T, ID> repository;
    protected UserEntityRepository userEntityRepository;

    public UserAbstractService(EntityRepository<T, ID> repository){
        this.repository = repository;
    }

    @Override
    public void delete(String id){
        userEntityRepository.deleteUsersById(id);
    }

    @Override
    public User findUserByEmail(String email){
        return userEntityRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByPhone(String phoneNumber){
        return userEntityRepository.findUserByPhone(phoneNumber);
    }
}
