package com.resource.service.repository;

import com.resource.service.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;


@NoRepositoryBean
public interface EntityRepository <T,ID extends Serializable> extends MongoRepository<T, ID> {
    void deleteById(ID id);
    @Override
    List<T> findAll();
}
