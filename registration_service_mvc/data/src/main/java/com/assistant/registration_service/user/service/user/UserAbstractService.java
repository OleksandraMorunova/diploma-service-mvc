package com.assistant.registration_service.user.service.user;

import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.model_data.model.resource_service.UserAndTasks;
import com.assistant.registration_service.user.repository.EntityRepository;
import com.assistant.registration_service.user.repository.UserEntityRepository;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public abstract class UserAbstractService<T, ID extends Serializable> implements UserServiceInterface<T, ID> {
    protected EntityRepository<T, ID> repository;
    protected UserEntityRepository userEntityRepository;

    public UserAbstractService(EntityRepository<T, ID> repository){
        this.repository = repository;
    }

    @Override
    public void delete(String email){
        userEntityRepository.deleteUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email){
        return userEntityRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByPhone(String phoneNumber){
        return userEntityRepository.findUserByPhone(phoneNumber);
    }

    @Override
    public User findUserByEmailOrPhoneAndStatus(String value){
        return userEntityRepository.findUserByCode(value);
    }
}
