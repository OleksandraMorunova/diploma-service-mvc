package com.assistant.registration_service.user.service.one_time_password;

import com.assistant.registration_service.user.repository.EntityRepository;

import java.io.Serializable;

public abstract class AbstractOneTimeCodeService<T, ID extends Serializable> implements OneTimeCodeServiceInterface<T, ID> {
    protected EntityRepository<T, ID> repository;

    public AbstractOneTimeCodeService(EntityRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T sentToPhoneNumberAndDataBase(T phoneNumber) {
        return repository.save(phoneNumber);
    }

    @Override
    public T deleteCode(ID code) {
        return repository.findUserByCode(null);
    }

    @Override
    public T sentToEmailAndDataBase(T user) {
        return repository.save(user);
    }
}
