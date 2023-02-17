package com.assistant.api.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface EntityRepository<T,ID extends Serializable> extends MongoRepository<T, ID> {
    Optional <T> findUserByEmail(ID email);
    T findByEmail(ID email);
    void deleteUserByEmail(ID email);

    List<T> findAllByRoleOrderByFirstname(ID roleName);
}
