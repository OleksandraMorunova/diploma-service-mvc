package com.assistant.registration_service.user.service.user;

import com.assistant.registration_service.user.repository.EntityRepository;

import java.io.Serializable;

public abstract class UserAbstractService<T, ID extends Serializable> implements UserServiceInterface<T, ID> {
    protected EntityRepository<T, ID> repository;

    public UserAbstractService(EntityRepository<T, ID> repository){
        this.repository = repository;
    }

    @Override
    public T saveUser(T entity){
        return repository.save(entity);
    }

    @Override
    public T delete(ID email){
        return repository.deleteUserByEmail(email);
    }

    @Override
    public T findUserByEmail(ID email){
        return repository.findUserByEmail(email);
    }

    @Override
    public T findUserByPhone(ID phoneNumber){
        return repository.findUserByPhone(phoneNumber);
    }

    @Override
    public T findAllByStatusAndRolesOrderByName(ID status){
        return repository.findAllByStatusAndRolesOrderByName(status, null);
    }

    @Override
    public T findUserByEmailOrPhoneAndStatus(ID value){
        return repository.findUserByCode(value);
    }
}
