package com.resource.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;


@NoRepositoryBean
public interface EntityRepository <T,ID extends Serializable> extends MongoRepository<T, ID> {
    T findTaskById(ID id);
    List<T> findAllByUserId(ID userId);
    void deleteById(ID id);
}
