package com.assistant.api.user.service;

import com.assistant.api.user.repository.EntityRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T, ID extends Serializable> implements Service<T, ID>{
    protected EntityRepository<T, ID> repository;

    public AbstractService(EntityRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public void save(T entity) { this.repository.save(entity);}

    @Override
    public T update(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(ID email) {
        repository.deleteUserByEmail(email);
    }

    @Override
    public T findByEmail(ID email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<T> findUserByEmail(ID email) {
        return repository.findUserByEmail(email);
    }

    @Override
    public List<T> findAllByRoleOrderByFirstname(ID roleName) {
        return repository.findAllByRoleOrderByFirstname(roleName);
    }
}
