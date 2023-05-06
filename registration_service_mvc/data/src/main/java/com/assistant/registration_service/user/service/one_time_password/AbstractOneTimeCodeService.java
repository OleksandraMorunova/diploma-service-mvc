package com.assistant.registration_service.user.service.one_time_password;

import com.assistant.registration_service.user.repository.EntityRepository;
import com.assistant.registration_service.user.repository.UserEntityRepository;

import java.io.Serializable;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

public abstract class AbstractOneTimeCodeService<T, ID extends Serializable> implements OneTimeCodeServiceInterface<T, ID> {
    protected EntityRepository<T, ID> repository;

    protected UserEntityRepository userEntityRepository;
    public AbstractOneTimeCodeService(EntityRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T sentToPhoneNumberAndDataBase(T phoneNumber) {
        return repository.save(phoneNumber);
    }

    @Override
    public void deleteCode(ID code) { userEntityRepository.findUserByCode(null);
    }

    @Override
    public T sentToEmailAndDataBase(T user) {
        return repository.save(user);
    }
}
