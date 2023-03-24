package com.assistant.registration_service.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface EntityRepository<T,ID extends Serializable> extends MongoRepository<T, ID> {
    T findUserByEmail(ID email);
    T findUserByPhone(ID phoneNumber);
    T findUserByCode(ID code);
    T deleteUserByEmail(ID email);
    T findAllByStatusAndRolesOrderByName(ID status, ID roles);
}
