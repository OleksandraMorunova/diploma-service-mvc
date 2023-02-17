package com.assistant.api.user.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Service<T, ID extends Serializable> {
    void save(T entity);
    T update(T entity);
    void delete(ID email);
    Optional <T> findUserByEmail(ID email);
    T findByEmail(ID email);
    List<T> findAllByRoleOrderByFirstname(ID roleName);
}
