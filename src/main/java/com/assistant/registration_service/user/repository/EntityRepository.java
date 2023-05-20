package com.assistant.registration_service.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

@NoRepositoryBean
public interface EntityRepository<T,ID extends Serializable> extends MongoRepository<T, ID> {
}
